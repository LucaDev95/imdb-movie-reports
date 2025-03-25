package com.luca.imdb_movie_rating.repository;

import com.luca.imdb_movie_rating.dto.MovieIdAndTConst;

import java.util.List;

public interface MovieRepositoryExtension {

    public List<MovieIdAndTConst> findMovieIdAndtConst(String tConst);
}
