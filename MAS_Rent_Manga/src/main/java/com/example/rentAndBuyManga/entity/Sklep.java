package com.example.rentAndBuyManga.entity;


import com.example.rentAndBuyManga.entity.enumeration.SklepStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
public class Sklep {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long sklep_id;

    private double kosztWynajmu;

    private double metrowKwadratowych;

    @Enumerated(EnumType.STRING)
    private SklepStatus status;

    @ManyToMany
    @JoinTable(name = "sklep_promocja", joinColumns = @JoinColumn(name = "sklep_id"), inverseJoinColumns = @JoinColumn(name = "promocja_id"))
    private Set<Promocja> promocjaSet;



}
