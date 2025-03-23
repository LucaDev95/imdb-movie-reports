package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.dtos.MovieIdAndTConst;
import com.luca.imdb_movie_rating.entities.Movie;

import java.util.List;

public interface MovieRepositoryExtension {

    public List<MovieIdAndTConst> findMovieIdAndtConst(String tConst);
}
