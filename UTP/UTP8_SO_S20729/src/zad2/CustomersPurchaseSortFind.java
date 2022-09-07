/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class CustomersPurchaseSortFind {

    private List<Purchase> purchaseList = null;
    private File file =  null;
    private Scanner sc = null;
    private Purchase purchase = null;

    public void readFile(String fname) {
        purchaseList = new ArrayList<Purchase>();
        try {
            file = new File(fname);
            sc = new Scanner(file);
            while (sc.hasNextLine()){
                String[] splited = sc.nextLine().split(";| ");
                purchase = new Purchase(splited[0],
                        splited[1],
                        splited[2],
                        splited[3],
                        Double.parseDouble(splited[4]),
                        Double.parseDouble(splited[5]),
                        Double.parseDouble(splited[4]) * Double.parseDouble(splited[5])
                );

                purchaseList.add(purchase);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Blad wczytania pliku in readFile constructor !");
        }
    }

    public void showSortedBy(String string) {
        Comparator<Purchase> byNazwisko = Comparator.comparing(Purchase::getNazwisko).thenComparing(Purchase::getId_klienta);
        Comparator<Purchase> byKoszty = Comparator.comparing(Purchase::getKoszt).thenComparing(Purchase::getId_klienta);
        switch (string) {
            case "Nazwiska":
                System.out.println(string);
                purchaseList.stream().sorted(byNazwisko).forEach(System.out::println);
                break;

            case "Koszty":
                System.out.println(string);
                purchaseList.stream().sorted(byKoszty).forEach(System.out::println);
                break;
        }

        System.out.println();
    }

    public void showPurchaseFor(String id) {
        System.out.println("Klient " + id);
        purchaseList.stream().filter(e -> id.equals(e.getId_klienta())).forEach(System.out::println);
        System.out.println();
    }
}
