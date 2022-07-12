package com.example.rentAndBuyManga.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table
@Entity
@Data
@NoArgsConstructor
public class Uszkodzenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(value = 100)
    @NotNull
    private int procentUszkodzenia;

    @Size(min = 3, max = 20, message = "Rodzaj szkody nie musi przekraczać 20 symboli")
    @NotEmpty
    private String rodzajSzkody;

    @NotNull
    private double wartoscOdszkodowania;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "towar_id", nullable = false)
    @JsonBackReference(value = "uszkodzenia-manga")
    private Manga manga;


}
