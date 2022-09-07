/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package web1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Currency;
import java.util.Locale;

public class Service {
    String kraj;
    String code;
    String money;
    boolean przelocznik = false;
    String doWaluty = "";

    public Service(String kraj){
        this.kraj = kraj;
        Locale.setDefault(Locale.ENGLISH);
        for (Locale l :
                Locale.getAvailableLocales()) {
            if (l.getDisplayCountry().equals(kraj)) {
                code = l.getCountry();
                money = Currency.getInstance(l).getCurrencyCode();
            }
        }
    }

    public String getWeather(String miasto){
        String apiKey = "df8b314f96e2ef23605d237acafd551d";
        String json = "";
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+miasto +"," + code + "&units=metric&APPID="+apiKey);

            JSONParser jsonParser = new JSONParser();

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream(), "UTF-8"))) {
                String line;
                while ((line = in.readLine()) != null) json += line;
            }
            //System.out.println(json);
            JSONObject jsonObject = (JSONObject)jsonParser.parse(json);
            JSONObject jsonObject1 = (JSONObject)jsonObject.get("main");
            json=jsonObject1.toJSONString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }

    public double getRateFor(String waluta){
        doWaluty=waluta;
        String json = "";
        double rez=0;
        try {
            URL url = new URL("https://api.exchangerate.host/latest?base="+money+ "&symbols="+waluta);
            JSONParser jsonParser = new JSONParser();

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream(), "UTF-8"))) {
                String line;
                while ((line = in.readLine()) != null) json += line;
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = (JSONObject)jsonParser.parse(json);
            JSONObject jsonObject1 = (JSONObject) jsonObject.get("rates");
            rez = (double)jsonObject1.get(waluta);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rez;
    }

    public double getNBPRate(){
        if (!doWaluty.equals("PLN")){
            String json="";
            double rez=0;
            try{
                URL url = getURLforNBP(przelocznik);
                JSONParser jsonParser = new JSONParser();

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8")) ;
                String line;
                while ((line = in.readLine()) != null) json += line;

                JSONObject jsonObject = (JSONObject)jsonParser.parse(json);
                JSONArray jsonArray = (JSONArray) jsonObject.get("rates");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                rez = (double) jsonObject1.get("mid");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e){
                przelocznik=!przelocznik;
                getNBPRate();
            } catch (IOException e) {
                e.printStackTrace();
            }
            przelocznik = false;
            return rez;
        }else{
            return 1.0;
        }
    }
    public URL getURLforNBP(boolean przelocznik) throws MalformedURLException {
        if (przelocznik == false){
            return new URL("http://api.nbp.pl/api/exchangerates/rates/a/"+doWaluty+"/?format=json");
        }else {
            return new URL("http://api.nbp.pl/api/exchangerates/rates/b/"+doWaluty+"/?format=json");
        }
    }
}

