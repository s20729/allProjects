package com.example.rentAndBuyManga.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Administrator extends Pracownik{


    @Nullable
    private String poprzednieMiejscePracy;

    private String wyksztalcenie;

}
