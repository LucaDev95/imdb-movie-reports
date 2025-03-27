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

    private final TsvReaderService tsvReaderService;

    private final RatingService ratingService;

    private final SummaryService summaryService;

    private final EmailService emailService;

    private final ReportService reportService;

    public MainServiceImpl(MovieService movieService, TsvReaderService tsvReaderService, RatingService ratingService, SummaryService summaryService, EmailService emailService, ReportService reportService) {
        this.movieService = movieService;
        this.tsvReaderService = tsvReaderService;
        this.ratingService = ratingService;
        this.summaryService = summaryService;
        this.emailService = emailService;
        this.reportService = reportService;

    }

    @Override
    public void loadDaily() {



        logger.info("loadDaily start");

        try {
            logger.info("deleting today records");

            summaryService.deleteTodaySummary();

            movieService.deleteCurrentDate();

            ratingService.deleteCurrentRatings();

            summaryService.deleteTodaySummary();

            logger.info("loading new movies and ratings");

            tsvReaderService.readTitlesTsv();

            tsvReaderService.readRatingsTsv();

            logger.info("loadDaily completed successfully");

            logger.info("searching top 100 trending movies");

            List<TrendingMovieDto> trendingMoviesList = summaryService.getDailyTrendingMoviesSummary();

            logger.info("generating trending movies report");

            String trendingReport = reportService.generateTrendingMoviesReport(trendingMoviesList);

            logger.info("trending movies report generated");

            summaryService.saveDailyTrendingMovies(trendingMoviesList);

            DailySummaryDto summaryDto = summaryService.calculateDailySummary();

            List<DailySummaryGenreDto> summaryGenreDtoList = summaryService.calculateDailySummaryByGenre();

            String summaryReport = reportService.generateDailySummaryReport(summaryDto, summaryGenreDtoList);
            summaryService.saveDailySummary(summaryDto, summaryGenreDtoList);

            emailService.sendSummaryMail(trendingReport, summaryReport);


        } catch (ApplicationException e) {
            logger.error("Error during loadDaily", e);

            emailService.sendErrorMail(e);

        } catch (Exception e) {
            logger.error("unhandled exception ", e);
        }


    }

}
