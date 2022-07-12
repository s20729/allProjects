package com.example.rentAndBuyManga.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class Towar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long towar_id;

    @NotNull
    private double cena;

    private String opis;

    @ManyToOne
    @JoinColumn(name = "zamowienie_id", nullable = true)
    private Zamowienie zamowienie;

    public Towar(double cena, String opis) {
        this.cena = cena;
        this.opis = opis;
    }

}
