package com.example.rentAndBuyManga.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Dostawca extends Pracownik{

    private String kategoriaPrawaJazdy;

    private String rodzajTransportu;

    @OneToMany(mappedBy = "dostawca")
    @JsonBackReference(value = "dostawca-zamowienie")
    private List<Zamowienie> zamowienieList;
}
