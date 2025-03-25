package com.luca.imdb_movie_rating.service.impl;


import com.luca.imdb_movie_rating.dto.DailySummaryDto;
import com.luca.imdb_movie_rating.dto.DailySummaryGenreDto;
import com.luca.imdb_movie_rating.dto.TrendingMovieDto;
import com.luca.imdb_movie_rating.service.EmailService;
import com.luca.imdb_movie_rating.service.ReportService;
import com.luca.imdb_movie_rating.service.SummaryCalculatorService;
import com.luca.imdb_movie_rating.service.SummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryCalculatorServiceImpl implements SummaryCalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(SummaryCalculatorServiceImpl.class);

    private final SummaryService summaryService;

    private final ReportService reportService;

    private final EmailService emailService;

    public SummaryCalculatorServiceImpl(SummaryService summaryService,ReportService reportService,EmailService emailService){
        this.summaryService=summaryService;
        this.reportService=reportService;
        this.emailService=emailService;
    }


    @Override
    @Async
    public void calculateSummary() {


        summaryService.deleteTodaySummary();

        logger.info("searching top 100 trending movies");

        List<TrendingMovieDto> trendingMoviesList=summaryService.getDailyTrendingMoviesSummary();

        logger.info("generating trending movies report");

        reportService.generateTrendingMoviesReport(trendingMoviesList);

        logger.info("trending movies report generated");

        summaryService.saveDailyTrendingMovies(trendingMoviesList);


        DailySummaryDto summaryDto =summaryService.calculateDailySummary();

        List<DailySummaryGenreDto> summaryGenreDtoList=summaryService.calculateDailySummaryByGenre();

        reportService.generateDailySummaryReport(summaryDto,summaryGenreDtoList);
        summaryService.saveDailySummary(summaryDto);

        summaryService.saveDailySummaryGenreList(summaryGenreDtoList);

        //emailService.sendSummaryMail();


    }
}
