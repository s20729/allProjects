package com.example.rentAndBuyManga.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class Figurka extends Towar{

    private String nazwaPostaci;

    private String producent;

    private String uniwersum;
}
