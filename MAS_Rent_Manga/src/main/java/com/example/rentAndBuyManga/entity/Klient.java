package com.example.rentAndBuyManga.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Data
public class Klient extends Osoba{
    private boolean czyJestKlientemStalym;

    @OneToMany(mappedBy = "klient")
    @JsonBackReference(value = "wypozyczenie-klient")
    private List<Wypozyczenie> wypozyczenieList;

    @OneToOne(mappedBy = "klient")
    private Opinie opinie;

    public Klient(String imie, String nazwisko, int numerTelefonu, Date dataUrodzenia, boolean czyJestKlientemStalym, List<Wypozyczenie> wypozyczenieList) {
        super(imie, nazwisko, numerTelefonu, dataUrodzenia);
        this.czyJestKlientemStalym = czyJestKlientemStalym;
        this.wypozyczenieList = wypozyczenieList;
    }

}
