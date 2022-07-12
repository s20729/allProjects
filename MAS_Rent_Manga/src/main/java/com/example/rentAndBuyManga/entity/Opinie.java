package com.example.rentAndBuyManga.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
@Table
@NoArgsConstructor
public class Opinie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long opinie_id;

    @OneToOne
    @JoinColumn(name = "osoba_id")
    private Klient klient;

    private String komentarz;

    @Min(value = 1)
    @Max(value = 5)
    private int ocena;

    private int terminDostawyWDniach;

    @ManyToOne
    @JoinColumn(name = "zamowienie_id", nullable = true)
    @JsonBackReference(value = "zamowienie-opinie")
    private Zamowienie zamowienie;
}
