package com.example.rentAndBuyManga.repository;


import com.example.rentAndBuyManga.entity.Pracownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracownikRepository extends JpaRepository<Pracownik, Long> {
}
