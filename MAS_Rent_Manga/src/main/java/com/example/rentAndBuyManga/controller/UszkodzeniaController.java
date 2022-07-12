package com.example.rentAndBuyManga.controller;


import com.example.rentAndBuyManga.entity.Pracownik;
import com.example.rentAndBuyManga.entity.Towar;
import com.example.rentAndBuyManga.entity.Uszkodzenia;
import com.example.rentAndBuyManga.repository.UszkodzeniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UszkodzeniaController {

    @Autowired
    UszkodzeniaRepository uszkodzeniaRepository;


    @GetMapping("/uszkodzenie")
    public ResponseEntity<List<Uszkodzenia>> getAllUszkodzenia(){
        try{
            List<Uszkodzenia> uszkodzeniaList = new ArrayList<>();
            uszkodzeniaList.addAll(uszkodzeniaRepository.findAll());
            if (uszkodzeniaList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(uszkodzeniaList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uszkodzenie")
    public ResponseEntity<Uszkodzenia> addUszkodzenie(@Valid @RequestBody Uszkodzenia uszkodzenia){
        try {
            Uszkodzenia uszkodzenia1 = uszkodzeniaRepository.save(uszkodzenia);
            return new ResponseEntity<>(uszkodzenia1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/uszkodzenie/set/{uszkodzenieId}/{mangaId}")
    public ResponseEntity<Uszkodzenia> addUszkodzenie(@PathVariable Long uszkodzenieId, @PathVariable Long mangaId){
        try {
            var uszkodzenia1 = uszkodzeniaRepository.setMangaToUszkodzenie(uszkodzenieId, mangaId);
            return new ResponseEntity<>(uszkodzenia1.get(), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
