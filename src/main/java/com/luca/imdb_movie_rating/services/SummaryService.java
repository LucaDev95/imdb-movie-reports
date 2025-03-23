package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.dtos.DailySummaryDto;
import com.luca.imdb_movie_rating.dtos.DailySummaryGenreDto;
import com.luca.imdb_movie_rating.dtos.TrendingMovieDto;


import java.util.List;

public interface SummaryService {


    public DailySummaryDto calculateDailySummary();

    public List<DailySummaryGenreDto> calculateDailySummaryByGenre();

    public List<TrendingMovieDto> getDailyTrendingMoviesSummary();

    public void saveDailyTrendingMovies(List<TrendingMovieDto> trendingMoviesList);

    public void saveDailySummary(DailySummaryDto dailySummaryDto);

    public void saveDailySummaryGenreList(List<DailySummaryGenreDto> dailySummaryDto);

    public void deleteTodaySummary();

}
