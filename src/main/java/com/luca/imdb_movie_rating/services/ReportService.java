package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.dtos.DailySummaryDto;
import com.luca.imdb_movie_rating.dtos.DailySummaryGenreDto;
import com.luca.imdb_movie_rating.dtos.TrendingMovieDto;
import com.luca.imdb_movie_rating.entities.DailyGenreSummary;
import com.luca.imdb_movie_rating.entities.DailySummary;

import java.util.List;

public interface ReportService {

    public String generateTrendingMoviesReport(List<TrendingMovieDto> trendingMovieDtoList);

    public String generateDailySummaryReport(DailySummaryDto dailySummary, List<DailySummaryGenreDto> dailyGenreSummary);
}
