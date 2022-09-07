/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad4;


import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Time {

    public static String passed(String from, String to){
        StringBuilder output = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.##");
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("d MMMM yyyy (EEEE)", new Locale("pl"));

        if (from.contains("T") || to.contains("T")) {
            try {
                DateTimeFormatter pattern2 = DateTimeFormatter.ofPattern("HH:mm");
                ZonedDateTime dateFrom = ZonedDateTime.of(LocalDateTime.parse(from), ZoneId.of("Europe/Warsaw"));
                ZonedDateTime dateTo = ZonedDateTime.of(LocalDateTime.parse(to), ZoneId.of("Europe/Warsaw"));

                long sumDays = ChronoUnit.DAYS.between(dateFrom.toLocalDate(), dateTo.toLocalDate());
                double sumWeeks = (double) sumDays / 7;

                Duration duration = Duration.between(dateFrom, dateTo);
                Period period = Period.between(dateFrom.toLocalDate(), dateTo.toLocalDate());

                output.append("Od ").append(dateFrom.format(pattern)).append(" godz. ").append(dateFrom.format(pattern2)).append(" do ").append(dateTo.format(pattern)).append(" godz. ").append(dateTo.format(pattern2))
                        .append("\n - mija: ").append(sumDays).append(getDay(period.getDays())).append(", tygodni ").append(df.format(sumWeeks))
                        .append("\n - godzin: ").append(duration.toHours()).append(", minut: ").append(duration.toMinutes()).append(getCalendar(period));

            } catch (DateTimeParseException ex) {
                output.append("***").append(" ").append(ex.toString());
            }
        } else {
            try {
                LocalDate dateFrom = LocalDate.parse(from);
                LocalDate dateTo = LocalDate.parse(to);

                long sumDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
                double sumWeeks = (double) sumDays / 7;
                Period period = Period.between(dateFrom, dateTo);

                output.append("Od ").append(dateFrom.format(pattern)).append(" do ").append(dateTo.format(pattern))
                        .append("\n - mija: ").append(sumDays).append(" dni, tygodni ").append(df.format(sumWeeks)).append(getCalendar(period));


            } catch (DateTimeParseException ex) {
                output.append("***").append(" ").append(ex.toString());
            }
        }
        return String.valueOf(output);
    }

    private static StringBuilder getCalendar(Period period) {
        StringBuilder output = new StringBuilder();
        if (period.getYears() >= 1) {
            output.append("\n - kalendarzowo: ").append(period.getYears()).append(getYear(period.getYears()));
            if (period.getMonths() >= 1) {
                output.append(", ").append(period.getMonths()).append(getMonth(period.getMonths()));
                if (period.getDays() >= 1) {
                    output.append(", ").append(period.getDays()).append(getDay(period.getDays()) + "\n");
                }
            }
        } else if (period.getMonths() >= 1) {
            output.append("\n - kalendarzowo: ").append(period.getMonths()).append(getMonth(period.getMonths()));
            if (period.getDays() >= 1) {
                output.append(", ").append(period.getDays()).append(getDay(period.getDays()) + "\n");
            }
        } else if (period.getDays() >= 1) {
            output.append("\n - kalendarzowo: ").append(period.getDays()).append(getDay(period.getDays()) + "\n");
        }

        return output;
    }

    private static String getDay(int numberOfDays) {
        return numberOfDays == 1 ? " dzień" : " dni";
    }

    private static String getMonth(int numberOfMonths) {
        if (numberOfMonths == 1) {
            return " miesiąc";
        } else if (Integer.toString(numberOfMonths).endsWith("2") || Integer.toString(numberOfMonths).endsWith("3") || Integer.toString(numberOfMonths).endsWith("4")) {
            return " miesiące";
        } else {
            return " miesięcy";
        }
    }

    private static String getYear(int numberOfYears) {
        if (numberOfYears == 1) {
            return " rok";
        } else if (Integer.toString(numberOfYears).endsWith("2") || Integer.toString(numberOfYears).endsWith("3") || Integer.toString(numberOfYears).endsWith("4")) {
            return " lata";
        } else {
            return " lat";
        }
    }
}
