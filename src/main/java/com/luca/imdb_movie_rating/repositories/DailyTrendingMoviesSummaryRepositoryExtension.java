package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.dtos.TrendingMovieDto;

import java.util.List;

public interface DailyTrendingMoviesSummaryRepositoryExtension {

    public List<TrendingMovieDto> findDailyTrendingMoviesSummary();

}
