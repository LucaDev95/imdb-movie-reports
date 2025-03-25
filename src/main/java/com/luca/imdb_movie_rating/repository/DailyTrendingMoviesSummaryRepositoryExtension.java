package com.luca.imdb_movie_rating.repository;

import com.luca.imdb_movie_rating.dto.TrendingMovieDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface DailyTrendingMoviesSummaryRepositoryExtension {

    public List<TrendingMovieDto> findDailyTrendingMoviesSummary(LocalDate startDate, LocalDate currentDate) throws IOException;

}
