package com.example.rentAndBuyManga.repository;


import com.example.rentAndBuyManga.entity.Manga;
import com.example.rentAndBuyManga.entity.Towar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface TowarRepository extends CrudRepository {
}
