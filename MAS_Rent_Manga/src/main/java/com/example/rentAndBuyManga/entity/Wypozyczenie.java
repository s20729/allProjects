package com.example.rentAndBuyManga.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Wypozyczenie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wypozyczenie_id;

    @NotNull
    private Date data;

    private LocalDateTime dataEnd;

    private double dodatkowaKwota;

    @Max(value = 3)
    private int iloscDni;

    private double kwotaWynajmu;

    @OneToMany(mappedBy = "wypozyczenie")
    @JsonBackReference(value = "wypozyczenie-manga")
    private List<Manga> mangaList;

    @ManyToOne
    @JoinColumn(name = "osoba_id", nullable = false)
    @NotNull
    @JsonBackReference(value = "wypozyczenie-klient")
    private Klient klient;



    public Wypozyczenie(Date data, double dodatkowaKwota, int iloscDni, double kwotaWynajmu, List<Manga> mangaList, Klient klient) {
        this.data = data;
        this.dodatkowaKwota = dodatkowaKwota;
        this.iloscDni = iloscDni;
        this.kwotaWynajmu = kwotaWynajmu;
        this.mangaList = mangaList;
        this.klient = klient;
    }
}
