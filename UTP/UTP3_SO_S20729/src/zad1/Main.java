/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad1;


import java.util.*;

public class Main {

  static List<String> getPricesInPLN(List<String> destinations, double xrate) {
    return ListCreator.collectFrom(destinations)
                       .when( n -> n.startsWith("WAW") /*<-- lambda wyrażenie
                                *  selekcja wylotów z Warszawy (zaczynających się od WAW)
                                */
                        )
                       .mapEvery(n -> {
                         String[] data = n.split(" ");
                         return "to " + data[1] + "- price in PLN: " + (int)(Double.parseDouble(data[2]) * xrate);
                               } /*<-- lambda wyrażenie
                                   *  wyliczenie ceny przelotu w PLN
                                   *  i stworzenie wynikowego napisu
                                   */
                        );
  }

  public static void main(String[] args) {
    // Lista destynacji: port_wylotu port_przylotu cena_EUR 
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
    for (String r : result) System.out.println(r);
  }
}
