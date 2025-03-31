package com.luca.imdb.movie.reports.service.impl;

import com.luca.imdb.movie.reports.config.ExecutionProperties;
import com.luca.imdb.movie.reports.entitiy.GenreEntity;
import com.luca.imdb.movie.reports.enums.Genre;

import com.luca.imdb.movie.reports.repository.*;
import com.luca.imdb.movie.reports.service.ClearUpTablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class ClearUpTablesServiceImpl implements ClearUpTablesService {

    private static final Logger logger = LoggerFactory.getLogger(ClearUpTablesServiceImpl.class);

    private final MovieRepository movieRepository;

    private final RatingRepository ratingRepository;

    private final GenreRepository genreRepository;

    private final SummaryRepository dailySummaryRepository;

    private final GenreSummaryRepository dailySummaryGenreRepository;

    private final TrendingMoviesSummaryRepository dailyTrendingMoviesSummaryRepository;

    private final ExecutionProperties executionProperties;


    public ClearUpTablesServiceImpl(MovieRepository movieRepository, RatingRepository ratingRepository, GenreRepository genreRepository, SummaryRepository dailySummaryRepository,
                                    GenreSummaryRepository dailySummaryGenreRepository, TrendingMoviesSummaryRepository dailyTrendingMoviesSummaryRepository, ExecutionProperties executionProperties) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.genreRepository = genreRepository;
        this.dailySummaryRepository = dailySummaryRepository;
        this.dailySummaryGenreRepository = dailySummaryGenreRepository;
        this.dailyTrendingMoviesSummaryRepository = dailyTrendingMoviesSummaryRepository;
        this.executionProperties = executionProperties;

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

        List<GenreEntity> genreEntityList = Arrays.stream(Genre.values()).map(GenreEntity::new).toList();

        genreRepository.saveAll(genreEntityList);
    }

    @Override
    @Transactional
    public void deleteOldMoviesData() {

        LocalDate currentDate = executionProperties.getCurrentDate();

        LocalDate limitDate = currentDate.minusDays(executionProperties.getMaxRatingDays());

        Integer limitYear = currentDate.getYear() - executionProperties.getMaxPreviousYears();

        dailyTrendingMoviesSummaryRepository.deleteOldTrendingMovies(limitDate, limitYear);

        ratingRepository.deleteOldRatings(limitDate, limitYear);

        movieRepository.deleteOldMoviesGenre(limitDate, limitYear);

        int oldMovies=movieRepository.deleteOldMovies(limitDate, limitYear);

        logger.info("deleted {} old movies",oldMovies);

    }


}
