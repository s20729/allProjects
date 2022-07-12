package com.example.rentAndBuyManga.repository;


import com.example.rentAndBuyManga.entity.Uszkodzenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UszkodzeniaRepository extends JpaRepository<Uszkodzenia, Long> {

    @Modifying
    @Query("update Uszkodzenia u set u.manga.towar_id = ?2 where u.id=?1")
    Optional<Uszkodzenia> setMangaToUszkodzenie(Long uszkodzenieId, Long mangaId);

}
