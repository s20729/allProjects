package com.example.rentAndBuyManga.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Pracownik extends Osoba{
    @NotNull
    private Long pesel;

    @NotNull
    private Date dataZatrudnienia;

    @NotNull
    private double pensja;

    @NotNull
    private double premia;

    public Pracownik(String imie, String nazwisko, int numerTelefonu, Date dataUrodzenia, Long pesel, Date dataZatrudnienia, double pensja, double premia) {
        super(imie, nazwisko, numerTelefonu, dataUrodzenia);
        this.pesel = pesel;
        this.dataZatrudnienia = dataZatrudnienia;
        this.pensja = pensja;
        this.premia = premia;
    }
}
