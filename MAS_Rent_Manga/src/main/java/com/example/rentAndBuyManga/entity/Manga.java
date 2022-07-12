package com.example.rentAndBuyManga.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Data
public class Manga extends Towar{
    @NotEmpty
    private String autor;

    @NotNull
    private boolean czyJestUszkodzona;

    @NotEmpty
    private String tytul;

    @OneToMany(mappedBy = "manga")
    @JsonBackReference(value = "uszkodzenia-manga")
    private List<Uszkodzenia> uszkodzeniaList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wypozyczenie_id", nullable = true)
    @JsonBackReference(value = "wypozyczenie-manga")
    private Wypozyczenie wypozyczenie;

    private String status;

    public Manga(double cena, String opis, String autor, boolean czyJestUszkodzona, String tytul, List<Uszkodzenia> uszkodzeniaList, Wypozyczenie wypozyczenie) {
        super(cena, opis);
        this.autor = autor;
        this.czyJestUszkodzona = czyJestUszkodzona;
        this.tytul = tytul;
        this.uszkodzeniaList = uszkodzeniaList;
        this.wypozyczenie = wypozyczenie;
    }
}
