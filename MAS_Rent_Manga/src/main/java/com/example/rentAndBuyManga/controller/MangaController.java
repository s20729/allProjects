package com.example.rentAndBuyManga.controller;

import com.example.rentAndBuyManga.entity.Manga;
import com.example.rentAndBuyManga.entity.Towar;
import com.example.rentAndBuyManga.entity.Wypozyczenie;
import com.example.rentAndBuyManga.repository.MangaRepository;
import com.example.rentAndBuyManga.repository.WypozyczenieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class MangaController {

    @Autowired
    MangaRepository mangaRepository;


    @GetMapping("/manga")
    public ResponseEntity<List<Manga>> getAllManga(){
        try{
            List<Manga> mangaList = new ArrayList<>();
            mangaRepository.findAll().forEach(mangaList::add);
            if (mangaList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(mangaList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/manga")
    public ResponseEntity<Towar>addManga(@Valid @RequestBody Manga manga){
        try {
            Towar manga1 = mangaRepository.save(manga);
            return new ResponseEntity<>(manga1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/manga/wait/{id}")
    public ResponseEntity<Manga> changeStatusToWaitByMangaId(@PathVariable Long id){
            var manga = mangaRepository.findMangaByTowar_id(id);
            if (manga.isPresent()){

                Manga manga1 = manga.get();
                manga1.setStatus("WTRAKCIE");
                return new ResponseEntity<>(mangaRepository.save(manga1), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
    @PutMapping("/manga/received/{id}")
    public ResponseEntity<List<Manga>> changeStatusToReceivedByWypozyczenieId(@PathVariable Long id ){
        var manga = mangaRepository.findMangaByWypozyczenieWypozyczenie_id(id);
        if (!manga.isEmpty()){
            manga.forEach(i->i.setStatus("ZWROCONA"));
            return new ResponseEntity<>(mangaRepository.saveAll(manga), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
