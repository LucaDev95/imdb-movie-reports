package com.luca.imdb.movie.reports.repository;

import com.luca.imdb.movie.reports.dto.TrendingMovieDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface TrendingMoviesSummaryRepositoryExtension {

    public List<TrendingMovieDto> findTrendingMoviesSummary(LocalDate startDate, LocalDate currentDate) throws IOException;

}
