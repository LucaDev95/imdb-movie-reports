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
import java.util.concurrent.CompletableFuture;

@Service
public class MainServiceImpl implements MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

    private final MovieService movieService;

    private final TsvLoaderService tsvLoaderService;

    private final RatingService ratingService;

    private final SummaryService summaryService;

    private final EmailService emailService;

    private final ReportService reportService;

    private final ClearUpTablesService clearUpTablesService;

    public MainServiceImpl(MovieService movieService, TsvLoaderService tsvLoaderService, RatingService ratingService, SummaryService summaryService, EmailService emailService, ReportService reportService,ClearUpTablesService clearUpTablesService) {
        this.movieService = movieService;
        this.tsvLoaderService = tsvLoaderService;
        this.ratingService = ratingService;
        this.summaryService = summaryService;
        this.emailService = emailService;
        this.reportService = reportService;
        this.clearUpTablesService=clearUpTablesService;

    }

    @Override
    public void loadDaily() {

        logger.info("MainService start");

        try {
            logger.info("deleting today records");

            summaryService.deleteTodaySummary();

            ratingService.deleteCurrentRatings();

            movieService.deleteCurrentDate();

            logger.info("loading new movies and ratings");

            tsvLoaderService.loadTitles();

            tsvLoaderService.loadRatings();

            if(ratingService.checkReportGeneration()) {

                logger.info("Ratings are in summary range. Start generating reports");

                CompletableFuture<String> trendingMoviesFuture = CompletableFuture.supplyAsync(() -> processTrendingMovies());

                CompletableFuture<String> dailySummaryFuture = CompletableFuture.supplyAsync(() -> processDailySummary());

                CompletableFuture.allOf(trendingMoviesFuture, dailySummaryFuture).get();

                String trendingMoviesReport = trendingMoviesFuture.get();

                String dailySummaryReport = dailySummaryFuture.get();

                logger.info("sending reports mail");

                emailService.sendSummaryMail(trendingMoviesReport, dailySummaryReport);

            }

            clearUpTablesService.deleteOldMoviesData();

            logger.info("MainService completed successfully");

        } catch (ApplicationException e) {
            logger.error("Error during loadDaily", e);

            emailService.sendErrorMail(e);

        } catch (Exception e) {
            logger.error("unhandled exception ", e);
        }


    }

    private String processTrendingMovies() {
        logger.info("searching top 100 trending movies");

        List<TrendingMovieDto> trendingMoviesList = summaryService.getDailyTrendingMoviesSummary();

        logger.info("generating trending movies report");

        String trendingReport = reportService.generateTrendingMoviesReport(trendingMoviesList);

        logger.info("saving trending movies");

        summaryService.saveDailyTrendingMovies(trendingMoviesList);
        return trendingReport;
    }

    private String processDailySummary(){
        logger.info("calculating daily summary");

        DailySummaryDto summaryDto = summaryService.calculateDailySummary();

        List<DailySummaryGenreDto> summaryGenreDtoList = summaryService.calculateDailySummaryByGenre();

        logger.info("generating summary report");

        return reportService.generateDailySummaryReport(summaryDto, summaryGenreDtoList);
    }

}
