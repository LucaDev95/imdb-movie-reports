package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.entities.GenreEntity;
import com.luca.imdb_movie_rating.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<GenreEntity,Integer> {


    @Modifying
    @Query(value = "truncate Genre cascade",nativeQuery = true)
    public void truncate();


    public Optional<GenreEntity> findByGenre(Genre genre);
}
