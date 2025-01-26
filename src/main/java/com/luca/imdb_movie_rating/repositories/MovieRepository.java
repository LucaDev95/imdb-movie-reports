package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
