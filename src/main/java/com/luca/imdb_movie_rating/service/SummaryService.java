package com.luca.imdb_movie_rating.service;

import com.luca.imdb_movie_rating.dto.DailySummaryDto;
import com.luca.imdb_movie_rating.dto.DailySummaryGenreDto;
import com.luca.imdb_movie_rating.dto.TrendingMovieDto;


import java.io.IOException;
import java.util.List;

public interface SummaryService {


    public DailySummaryDto calculateDailySummary();

    public List<DailySummaryGenreDto> calculateDailySummaryByGenre();

    public List<TrendingMovieDto> getDailyTrendingMoviesSummary() throws IOException;

    public void saveDailyTrendingMovies(List<TrendingMovieDto> trendingMoviesList);

    public void saveDailySummary(DailySummaryDto dailySummaryDto,List<DailySummaryGenreDto> dailySummaryGenreDto);

    public void deleteTodaySummary();

}
