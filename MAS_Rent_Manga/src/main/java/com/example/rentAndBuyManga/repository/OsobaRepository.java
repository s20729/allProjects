package com.example.rentAndBuyManga.repository;

import com.example.rentAndBuyManga.entity.Osoba;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface OsobaRepository extends CrudRepository<Osoba, Long> {
}
