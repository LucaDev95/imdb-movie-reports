package com.luca.imdb.movie.reports.service.impl;


import com.luca.imdb.movie.reports.dto.TrendingMovieDto;
import com.luca.imdb.movie.reports.service.*;
import com.luca.imdb.movie.reports.dto.DailySummaryDto;
import com.luca.imdb.movie.reports.dto.DailySummaryGenreDto;
import com.luca.imdb.movie.reports.exception.ApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {

    private static final Logger logger = LoggerFactory.getLogger(ResetDataServiceImpl.class);

    private final MovieService movieService;

    private final TsvLoaderService tsvLoaderService;

    private final RatingService ratingService;

    private final SummaryService summaryService;

    private final EmailService emailService;

    private final ReportService reportService;

    public MainServiceImpl(MovieService movieService, TsvLoaderService tsvLoaderService, RatingService ratingService, SummaryService summaryService, EmailService emailService, ReportService reportService) {
        this.movieService = movieService;
        this.tsvLoaderService = tsvLoaderService;
        this.ratingService = ratingService;
        this.summaryService = summaryService;
        this.emailService = emailService;
        this.reportService = reportService;

    }

    @Override
    public void loadDaily() {



        logger.info("MainService start");

        try {
            logger.info("deleting today records");

            summaryService.deleteTodaySummary();

            movieService.deleteCurrentDate();

            ratingService.deleteCurrentRatings();

            summaryService.deleteTodaySummary();

            logger.info("loading new movies and ratings");

            tsvLoaderService.loadTitles();

            tsvLoaderService.loadRatings();

            logger.info("searching top 100 trending movies");

            List<TrendingMovieDto> trendingMoviesList = summaryService.getDailyTrendingMoviesSummary();

            logger.info("generating trending movies report");

            String trendingReport = reportService.generateTrendingMoviesReport(trendingMoviesList);

            logger.info("saving trending movies");

            summaryService.saveDailyTrendingMovies(trendingMoviesList);

            logger.info("calculating daily summary");

            DailySummaryDto summaryDto = summaryService.calculateDailySummary();

            List<DailySummaryGenreDto> summaryGenreDtoList = summaryService.calculateDailySummaryByGenre();

            logger.info("generating summary report");

            String summaryReport = reportService.generateDailySummaryReport(summaryDto, summaryGenreDtoList);

            logger.info("saving daily summary");
            summaryService.saveDailySummary(summaryDto, summaryGenreDtoList);

            logger.info("sending reports mail");

            emailService.sendSummaryMail(trendingReport, summaryReport);

            logger.info("MainService completed successfully");

        } catch (ApplicationException e) {
            logger.error("Error during loadDaily", e);

            emailService.sendErrorMail(e);

        } catch (Exception e) {
            logger.error("unhandled exception ", e);
        }


    }

}
