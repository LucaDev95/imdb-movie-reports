package com.luca.imdb.movie.reports.service;

import com.luca.imdb.movie.reports.dto.DailySummaryDto;
import com.luca.imdb.movie.reports.dto.DailySummaryGenreDto;
import com.luca.imdb.movie.reports.dto.TrendingMovieDto;

import java.util.List;

public interface ReportService {

    public String generateTrendingMoviesReport(List<TrendingMovieDto> trendingMovieDtoList);

    public String generateDailySummaryReport(DailySummaryDto dailySummary, List<DailySummaryGenreDto> dailyGenreSummary);
}
