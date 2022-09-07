/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad2;


public class Purchase {
    String id;
    String nazwisko;
    String imie;
    String towar;
    double cena;
    double zakupiona_ilość;
    double koszt;

    public Purchase(String id,String nazwisko, String imie, String towar, double cena, double zakupiona_ilość, double koszt) {
        this.id = id;
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.towar = towar;
        this.cena = cena;
        this.zakupiona_ilość = zakupiona_ilość;
        this.koszt = koszt;
    }

    @Override
    public String toString() {
        return id + ";" + nazwisko + " " + imie + ";" +towar + ";" + cena + ";" + zakupiona_ilość;
    }

    public String getId_klienta() {
        return id;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwa_towaru() {
        return towar;
    }

    public double getCena() {
        return cena;
    }

    public double getZakupiona_ilość() {
        return zakupiona_ilość;
    }

    public double getKoszt() {
        return koszt;
    }
}
