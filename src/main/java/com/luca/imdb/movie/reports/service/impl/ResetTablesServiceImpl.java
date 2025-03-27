package com.luca.imdb.movie.reports.service.impl;

import com.luca.imdb.movie.reports.entitiy.GenreEntity;
import com.luca.imdb.movie.reports.enums.Genre;

import com.luca.imdb.movie.reports.repository.*;
import com.luca.imdb.movie.reports.service.ResetTablesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ResetTablesServiceImpl implements ResetTablesService {

    private final MovieRepository movieRepository;

    private final RatingRepository ratingRepository;

    private final GenreRepository genreRepository;

    private final SummaryRepository dailySummaryRepository;

    private final GenreSummaryRepository dailySummaryGenreRepository;

    private final TrendingMoviesSummaryRepository dailyTrendingMoviesSummaryRepository;


    public ResetTablesServiceImpl(MovieRepository movieRepository, RatingRepository ratingRepository, GenreRepository genreRepository, SummaryRepository dailySummaryRepository,
                                  GenreSummaryRepository dailySummaryGenreRepository, TrendingMoviesSummaryRepository dailyTrendingMoviesSummaryRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.genreRepository = genreRepository;
        this.dailySummaryRepository=dailySummaryRepository;
        this.dailySummaryGenreRepository=dailySummaryGenreRepository;
        this.dailyTrendingMoviesSummaryRepository=dailyTrendingMoviesSummaryRepository;

    }



    @Override
    @Transactional
    public void resetTables() {

        dailyTrendingMoviesSummaryRepository.truncate();

        dailySummaryGenreRepository.truncate();

        dailySummaryRepository.truncate();

        ratingRepository.truncate();

        movieRepository.truncateMovieGenre();
        movieRepository.truncateMovie();

        genreRepository.truncate();

        List<GenreEntity> genreEntityList=  Arrays.stream(Genre.values()).map(GenreEntity::new).toList();

        genreRepository.saveAll(genreEntityList);
    }
}
