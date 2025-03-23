package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.entities.GenreEntity;
import com.luca.imdb_movie_rating.enums.Genre;
import com.luca.imdb_movie_rating.repositories.*;
import com.luca.imdb_movie_rating.services.ResetTablesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ResetTablesServiceImpl implements ResetTablesService {

    private final MovieRepository movieRepository;

    private final RatingRepository ratingRepository;

    private final GenreRepository genreRepository;

    private final DailySummaryRepository dailySummaryRepository;

    private final DailyGenreSummaryRepository dailySummaryGenreRepository;

    private final DailyTrendingMoviesSummaryRepository dailyTrendingMoviesSummaryRepository;


    public ResetTablesServiceImpl(MovieRepository movieRepository, RatingRepository ratingRepository, GenreRepository genreRepository,DailySummaryRepository dailySummaryRepository,
                                  DailyGenreSummaryRepository dailySummaryGenreRepository,DailyTrendingMoviesSummaryRepository dailyTrendingMoviesSummaryRepository) {
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
