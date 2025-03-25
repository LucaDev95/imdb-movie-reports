package com.luca.imdb_movie_rating.service;

import com.luca.imdb_movie_rating.dto.DailySummaryDto;
import com.luca.imdb_movie_rating.dto.DailySummaryGenreDto;
import com.luca.imdb_movie_rating.dto.TrendingMovieDto;

import java.util.List;

public interface ReportService {

    public String generateTrendingMoviesReport(List<TrendingMovieDto> trendingMovieDtoList);

    public String generateDailySummaryReport(DailySummaryDto dailySummary, List<DailySummaryGenreDto> dailyGenreSummary);
}
