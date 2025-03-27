package com.luca.imdb.movie.reports.repository;

import com.luca.imdb.movie.reports.entitiy.GenreEntity;
import com.luca.imdb.movie.reports.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<GenreEntity,Integer> {


    @Modifying
    @Query(value = "truncate Genre cascade",nativeQuery = true)
    public void truncate();


    public Optional<GenreEntity> findByGenreName(Genre genreName);
}
