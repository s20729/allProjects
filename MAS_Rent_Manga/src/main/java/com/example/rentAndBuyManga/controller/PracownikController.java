package com.example.rentAndBuyManga.controller;

import com.example.rentAndBuyManga.entity.Pracownik;
import com.example.rentAndBuyManga.repository.PracownikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PracownikController {

    @Autowired
    PracownikRepository pracownikRepository;

    @GetMapping("/pracownik")
    public ResponseEntity<List<Pracownik>> getAllUszkodzenia(){
        try{
            List<Pracownik> pracownikList = new ArrayList<>();
            pracownikList.addAll(pracownikRepository.findAll());
            if (pracownikList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(pracownikList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/pracownik")
    public ResponseEntity<Pracownik>addUszkodzenia(@Valid @RequestBody Pracownik pracownik){
        try {
            Pracownik pracownik1 = pracownikRepository.save(pracownik);
            return new ResponseEntity<>(pracownik1, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
