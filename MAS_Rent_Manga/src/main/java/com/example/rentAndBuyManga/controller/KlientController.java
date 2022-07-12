package com.example.rentAndBuyManga.controller;


import com.example.rentAndBuyManga.entity.Klient;
import com.example.rentAndBuyManga.entity.Manga;
import com.example.rentAndBuyManga.entity.Osoba;
import com.example.rentAndBuyManga.repository.KlientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class KlientController {

    @Autowired
    KlientRepository klientRepository;

    @GetMapping("/klient")
    public ResponseEntity<List<Klient>> getAllKlient(){
        try{
            List<Klient> klientList = new ArrayList<>();
            klientRepository.findAll().forEach(klientList::add);
            if (klientList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(klientList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/klient")
    public ResponseEntity<Osoba>addKlient(@Valid @RequestBody Klient klient){
        try {
            Osoba osoba1 = klientRepository.save(klient);
            return new ResponseEntity<>(osoba1, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/klient/{id}/manga")
    public ResponseEntity<List<Manga>> getListMangaByKlientId(@PathVariable Long id){
        try{
            var mangaList = klientRepository.getMangaListByOsobaId(id);
            if (mangaList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(mangaList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/klient/{id}")
    public ResponseEntity<Klient> getKlientById(@PathVariable Long id){
        try{
            var klient = klientRepository.findKlientByOsoba_id(id);
            return klient.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
