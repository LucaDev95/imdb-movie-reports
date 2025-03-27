package com.luca.imdb.movie.reports.repository;

import com.luca.imdb.movie.reports.dto.MovieIdAndTConst;

import java.util.List;

public interface MovieRepositoryExtension {

    public List<MovieIdAndTConst> findMovieIdAndTConst(String tConst);
}
