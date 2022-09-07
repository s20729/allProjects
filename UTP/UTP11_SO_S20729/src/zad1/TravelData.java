package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class TravelData {
    private ArrayList<ArrayList<String>> dataList = new ArrayList<>();
    private ArrayList<String> resList = null;
    private File file;
    private Scanner sc;
    private Locale localeSaved;

    public TravelData(File dataDir) {
        try {
            file = new File(dataDir + "\\dane");
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File read exe");
        }

        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] tokens = line.split("\\t");
            ArrayList<String> tmpList = new ArrayList<>();
            for (int i = 0; i < tokens.length; i++)//split na slowka i dodanie w tmp listu
                tmpList.add(tokens[i]);


            dataList.add(tmpList);
        }
    }

    public List<String> getOffersDescriptionsList(String localeString, String dateFormat) {
        resList = new ArrayList<>();
        String[] tokens = localeString.split("_");//split locale
        System.out.println(tokens[0]);
        String resString = new String();

        if(tokens.length > 1) {
            localeSaved = new Locale(tokens[0], tokens[1]);
        }else {
            localeSaved = new Locale(tokens[0]);
        }

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        simpleDateFormat.applyPattern(dateFormat);


        Date date = null;
        for (ArrayList<String> list : dataList) {
            String[] tokensTmp = list.get(0).split("_");
            Locale locale = null;

            if(tokensTmp.length > 1) {
                locale = new Locale(tokensTmp[0], tokensTmp[1]);
            }else {
                locale = new Locale(tokensTmp[0]);
            }

            Locale.setDefault(locale);

            Locale[] locales = Locale.getAvailableLocales();//wszyskti localy
            Locale localeTranslate = null;

            for (int i = 0; i < locales.length; i++) {
                if(locales[i].getDisplayCountry().equals(list.get(1)))
                    localeTranslate = locales[i];
            }//wyszukiwanie naszego localu ktory mamy w plikach
            resString += localeTranslate.getDisplayCountry(localeSaved);//zamiena nazw krajow na local nazwy
            System.out.println(resString);

            try {
                /*zamiena wszysktich nazw na localy*/
                date = simpleDateFormat.parse(list.get(2));
                resString += "	" + simpleDateFormat.format(date);

                date = simpleDateFormat.parse(list.get(3));
                resString += "	" + simpleDateFormat.format(date);

                ResourceBundle labels = ResourceBundle.getBundle("zad1.Bundle", localeSaved);
                resString += "	" + labels.getString(list.get(4));

                NumberFormat nFormat = NumberFormat.getInstance(locale);
                Number number = nFormat.parse(list.get(5));
                resString += "	" + nFormat.format(number);

                Currency currency = Currency.getInstance(list.get(6));
                resString += "	" + currency.getCurrencyCode();

                resList.add(resString);
                resString = new String();

            } catch (ParseException e) {
                System.err.println("Format exe");
            }
        }
        return resList;
    }

    public ArrayList<String> getResList() {
        return resList;
    }
}
