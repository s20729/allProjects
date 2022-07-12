package com.example.rentAndBuyManga.entity;

import com.example.rentAndBuyManga.entity.enumeration.ZamowienieEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Zamowienie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long zamowienie_id;

    private Date dataZamowienia;

    private double kwota;

    private String adresOdboiru;

    @Enumerated(EnumType.STRING)
    private ZamowienieEnum status;

    @OneToMany(mappedBy = "towar_id")
    @JsonBackReference(value = "zamowienie-towar")
    private List <Figurka> figurkaList;

    @OneToMany(mappedBy = "towar_id")
    private List <Manga> mangaList;

    @ManyToOne
    @JoinColumn(name = "osoba_id", nullable = false)
    @JsonBackReference(value = "dostawca-zamowienie")
    private Dostawca dostawca;

    @OneToMany(mappedBy = "opinie_id")
    @JsonBackReference(value = "zamowienie-opinie")
    private List<Opinie> opinieList;


}
