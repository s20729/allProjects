package com.example.rentAndBuyManga.controller;


import com.example.rentAndBuyManga.entity.Wypozyczenie;
import com.example.rentAndBuyManga.repository.MangaRepository;
import com.example.rentAndBuyManga.repository.WypozyczenieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WypozyczenieController {
    @Autowired
    WypozyczenieRepository wypozyczenieRepository;

    @Autowired
    MangaRepository mangaRepository;

    @PostMapping("/wypozyczenie")
    public HttpStatus addWypozyczenie(@Valid @RequestBody Wypozyczenie wypozyczenie){
        try {
            wypozyczenie.getMangaList().forEach(i->i.setStatus("ODEBRANA"));
            wypozyczenie.getMangaList().forEach(i->i.setWypozyczenie(wypozyczenie));
            mangaRepository.saveAll(wypozyczenie.getMangaList());
            return HttpStatus.OK;
        }catch (Exception e){
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    @GetMapping("/wypozyczenie/{status}")
    public ResponseEntity<List<Wypozyczenie>>getWypozyczenieByMangaStatus( @PathVariable String status){
        try {
            List<Wypozyczenie> wypozyczenie1 = wypozyczenieRepository.getWypozyczenieByMangaStatus(status);
            return new ResponseEntity<>(wypozyczenie1, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/wypozyczenie/{id}/{dodatkowaKwota}")
    public ResponseEntity<Wypozyczenie> changeStatusToReceivedAndDodatkowaKwotaByMangaId(@PathVariable Long id, @PathVariable double dodatkowaKwota){
        var wypozyczenie = wypozyczenieRepository.findById(id);
        if (wypozyczenie.isPresent()){
            Wypozyczenie wypozyczenie1 = wypozyczenie.get();
            wypozyczenie1.setDodatkowaKwota(dodatkowaKwota);
            wypozyczenie1.setDataEnd(LocalDateTime.now());
            return new ResponseEntity<>(wypozyczenieRepository.save(wypozyczenie1), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
