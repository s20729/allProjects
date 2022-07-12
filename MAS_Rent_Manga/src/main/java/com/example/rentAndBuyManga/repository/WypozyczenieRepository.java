package com.example.rentAndBuyManga.repository;

import com.example.rentAndBuyManga.entity.Manga;
import com.example.rentAndBuyManga.entity.Wypozyczenie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WypozyczenieRepository extends JpaRepository<Wypozyczenie, Long> {


    @Query("select w from Wypozyczenie w join Manga m on m.wypozyczenie.wypozyczenie_id = w.wypozyczenie_id where m.status = ?1")
    List<Wypozyczenie> getWypozyczenieByMangaStatus(String status);
}
