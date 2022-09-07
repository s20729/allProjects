/**
 *
 *  @author Popichko Yehor S21268
 *
 */

package zad4;



import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Time {

    public static Map<String, String> dayOfWeekMap;
    public static Map<Integer,String> monthMap;

    static {
        dayOfWeekMap = new HashMap<>();
        dayOfWeekMap.put("Sunday", "(niedziela)");
        dayOfWeekMap.put("Monday", "(poniedziałek)");
        dayOfWeekMap.put("Tuesday", "(wtorek)");
        dayOfWeekMap.put("Wednesday", "(środa)");
        dayOfWeekMap.put("Thursday", "(czwartek)");
        dayOfWeekMap.put("Friday", "(piątek)");
        dayOfWeekMap.put("Saturday", "(sobota)");
        monthMap = new HashMap<>();
        monthMap.put(1, "stycznia");
        monthMap.put(2, "lutego");
        monthMap.put(3, "marca");
        monthMap.put(4, "kwietnia");
        monthMap.put(5, "maja");
        monthMap.put(6, "czerwca");
        monthMap.put(7, "lipca");
        monthMap.put(8, "sierpnia");
        monthMap.put(9, "września");
        monthMap.put(10, "października");
        monthMap.put(11, "listopada");
        monthMap.put(12, "grudnia");
    }


    public static String passed(String from, String to) {
        String concat = "";
        if (isDateValid(from) && isDateValid(to)) {
            if (from.contains("T") || to.contains("T")) {
                if (from.split("T")[1].length() != 5) {
                    try {
                        throw new Exception("*** java.time.format.DateTimeParseException: Text " + from + " could not be parsed at index 13");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (to.split("T")[1].length() != 5) {
                    try {
                        throw new Exception("*** java.time.format.DateTimeParseException: Text " + to + " could not be parsed at index 13");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    return getInfo(from, to);
                }
            } else {
                Locale.setDefault(Locale.ENGLISH);
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(from);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                float mill = date.getTime();
                String dayFrom = new SimpleDateFormat("EEEE").format(date);
                String od = "Od " + (date.getDate()) + " " + monthMap.get(date.getMonth() + 1) + " " + (date.getYear() + 1900) + " " + dayOfWeekMap.get(dayFrom);
                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd").parse(to);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String dayTo = new SimpleDateFormat("EEEE").format(date1);
                String doV = " do " + (date1.getDate()) + " " + monthMap.get(date1.getMonth() + 1) + " " + (date1.getYear() + 1900) + " " + dayOfWeekMap.get(dayTo) + "\n";
                float mill1 = date1.getTime();
                DecimalFormat decimalFormat = new DecimalFormat("#######.##");
                DecimalFormat decimalFormat1 = new DecimalFormat("####");
                float diffDays = ((mill1 - mill) / (24 * 60 * 60 * 1000));
                float diffWeeks = (mill1 - mill) / (168 * 60 * 60 * 1000);
                String miJa;
                if (diffDays > 1) {
                    miJa = " - mija: " + decimalFormat1.format(diffDays) + " dni, tygodni " + decimalFormat.format(diffWeeks) + " \n";
                } else {
                    miJa = " - mija: " + decimalFormat1.format(diffDays) + " dzien, tygodni " + decimalFormat.format(diffWeeks) + " \n";
                }
                concat += od + doV + miJa;

                int rokDiff = (int) ChronoUnit.YEARS.between(LocalDate.parse(from),LocalDate.parse(to));
                int miesDiff = (int) ChronoUnit.MONTHS.between(LocalDate.parse(from),LocalDate.parse(to)) - (rokDiff * 12);
                int diffDayMonth = Math.abs(date.getDate() - date1.getDate());
                if (Integer.parseInt(decimalFormat1.format(diffDays)) >= 1) {
                    String calendar = " - kalendarzowo: " + (rokDiff >= 1 ? rokDiff > 1 ? rokDiff < 10 ? +rokDiff + " lata, " : +rokDiff + " lat, " : rokDiff + " rok, " : "") +
                            (miesDiff >= 1 ? miesDiff == 1 ? miesDiff + " miesiąc, " : miesDiff + " miesiące, " : "") + (diffDayMonth > 0 ? diffDayMonth == 1 ? diffDayMonth + " dzień" : diffDayMonth + " dni" : "") + "\n";
                    calendar = calendar.replaceAll(", $","");

                    concat += calendar;
                }

            }
            return concat;
        }else {
            if(!isDateValid(from)) {
                try {
                    throw new Exception("*** java.time.format.DateTimeParseException: Text '"+ from +"' could not be parsed: Invalid date 'February 29' as '2019' is not a leap year");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    throw new Exception("*** java.time.format.DateTimeParseException: Text '"+ to +"' could not be parsed: Invalid date 'February 29' as '2019' is not a leap year");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }



    public static String getInfo(String from, String to){
        ZonedDateTime zFrom = ZonedDateTime.of(LocalDateTime.parse(from), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime zTo = ZonedDateTime.of(LocalDateTime.parse(to), ZoneId.of("Europe/Warsaw"));
        Locale.setDefault(Locale.ENGLISH);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        float mill = date.getTime();
        String dayFrom = new SimpleDateFormat("EEEE").format(date);
        String od = "Od " + (date.getDate()) + " " + monthMap.get(date.getMonth() + 1) + " " + (date.getYear() + 1900) + " " + dayOfWeekMap.get(dayFrom) + " godz. " + from.split("T")[1];
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(to);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dayTo = new SimpleDateFormat("EEEE").format(date1);
        String doV = " do " + (date1.getDate()) + " " + monthMap.get(date1.getMonth() + 1) + " " + (date1.getYear() + 1900) + " " + dayOfWeekMap.get(dayTo) + " godz. " + to.split("T")[1] + " " + "\n";
        float mill1 = date1.getTime();
        DecimalFormat decimalFormat = new DecimalFormat("#######.##");
        DecimalFormat decimalFormat1 = new DecimalFormat("####");
        double diffDays = ChronoUnit.DAYS.between(zFrom.toLocalDate(),zTo.toLocalDate());
        double diffWeeks = diffDays/7;
        String miJa;
        if (diffDays != 1) {
            miJa = " - mija: " + decimalFormat1.format(diffDays) + " dni, tygodni " + decimalFormat.format(diffWeeks) + " \n";
        } else {
            miJa = " - mija: " + decimalFormat1.format(diffDays) + " dzien, tygodni " + decimalFormat.format(diffWeeks) + " \n";
        }
        String concat = "";
        concat += od + doV + miJa;
        int rokDiff = (date1.getYear() + 1900) - (date.getYear() + 1900);
        int miesDiff = (date1.getMonth() + 1) - (date.getMonth() + 1);
        int diffDayMonth = Math.abs(date.getDate() - date1.getDate());
        int diffHours = (int) Duration.between(zFrom,zTo).toHours();
        int diffMinutes = diffHours*60;
        String diffMin = " - godzin: " + diffHours + ", minut: " +diffMinutes;
        concat += diffMin;
        if (Integer.parseInt(decimalFormat1.format(diffDays)) >= 1) {
            String calendar = "\n - kalendarzowo: " + (rokDiff >= 1 ? rokDiff > 1 ? rokDiff < 10 ? +rokDiff + " lata, " : +rokDiff + " lat, " : rokDiff + " rok, " : "") +
                    (miesDiff >= 1 ? miesDiff == 1 ?   miesDiff + " miesiąc, " :  miesDiff + " miesiące, " : "") + (diffDayMonth > 0 ? diffDayMonth == 1 ? diffDayMonth + " dzień" :  diffDayMonth + " dni" : "") + "\n";
            calendar = calendar.replaceAll(", $","");
            concat += calendar;
        }
        return concat;
    }
    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
