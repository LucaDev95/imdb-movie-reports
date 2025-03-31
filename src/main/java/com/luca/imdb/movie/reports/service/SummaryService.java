package com.luca.imdb.movie.reports.service;

import com.luca.imdb.movie.reports.dto.DailySummaryDto;
import com.luca.imdb.movie.reports.dto.DailySummaryGenreDto;
import com.luca.imdb.movie.reports.dto.TrendingMovieDto;

import java.util.List;

public interface SummaryService {


    public DailySummaryDto calculateDailySummary();

    public List<DailySummaryGenreDto> calculateDailySummaryByGenre();

    public List<TrendingMovieDto> getDailyTrendingMoviesSummary();

    public void saveDailyTrendingMovies(List<TrendingMovieDto> trendingMoviesList);

    public void saveDailySummary(DailySummaryDto dailySummaryDto,List<DailySummaryGenreDto> dailySummaryGenreDto);

    public void deleteTodaySummary();

}
