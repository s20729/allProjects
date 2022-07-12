package com.example.rentAndBuyManga.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
public class Promocja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promocja_id;

    private String nazwa;

    @Min(value = 1)
    @Max(value = 50)
    private int procent;

    @ManyToMany(mappedBy = "promocjaSet")
    private Set<Sklep> sklepSet;
}
