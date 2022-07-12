package com.example.rentAndBuyManga.repository;

import com.example.rentAndBuyManga.entity.Manga;
import com.example.rentAndBuyManga.entity.Wypozyczenie;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {
    @Query("select m from Manga m where m.towar_id=?1")
    Optional<Manga> findMangaByTowar_id(Long Towar_id);

    @Query("select m from Manga m where m.wypozyczenie.wypozyczenie_id = ?1")
    List<Manga> findMangaByWypozyczenieWypozyczenie_id(Long id);
}
