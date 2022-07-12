package com.example.rentAndBuyManga.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class Osoba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long osoba_id;

    @NotEmpty
    private String imie;

    @NotEmpty
    private String nazwisko;

    @NotNull
    private int numerTelefonu;

    @NotNull
    private Date dataUrodzenia;

    public Osoba(String imie, String nazwisko, int numerTelefonu, Date dataUrodzenia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numerTelefonu = numerTelefonu;
        this.dataUrodzenia = dataUrodzenia;
    }
}
