package com.example.rentAndBuyManga.repository;

import com.example.rentAndBuyManga.entity.Klient;
import com.example.rentAndBuyManga.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KlientRepository extends JpaRepository<Klient, Long> {

    @Query("SELECT k FROM Klient k where k.osoba_id=?1")
    Optional<Klient> findKlientByOsoba_id(Long KlientByOsoba_id);

    @Query("select m from Manga m join Wypozyczenie w on w.klient.osoba_id = ?1 and w.dataEnd is null ")
    List<Manga> getMangaListByOsobaId(Long id);
}
