package com.luca.imdb_movie_rating.services.impl;


import com.luca.imdb_movie_rating.exceptions.ApplicationException;

import com.luca.imdb_movie_rating.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoadDailyServiceImpl implements  LoadDailyService{

    private static final Logger logger = LoggerFactory.getLogger(ResetDataServiceImpl.class);

    private final MovieService movieService;

    private final TsvReaderService tsvReaderService;

    private final RatingService ratingService;

    private final SummaryService summaryService;

    private final EmailService emailService;

    public LoadDailyServiceImpl(MovieService movieService,TsvReaderService tsvReaderService,RatingService ratingService,SummaryService summaryService,EmailService emailService){
        this.movieService=movieService;
        this.tsvReaderService=tsvReaderService;
        this.ratingService=ratingService;
        this.summaryService=summaryService;
        this.emailService=emailService;

    }

    @Override
    public void loadDaily() {

        LocalDate currentDate = LocalDate.now();

        logger.info("loadDaily start");

        try {
            logger.info("deleting today records");

        summaryService.deleteTodaySummary();

        movieService.deleteCurrentDate();

        ratingService.deleteCurrentRatings();

        logger.info("loading new movies and ratings");

        tsvReaderService.readTitlesTsv();

        tsvReaderService.readRatingsTsv();

        logger.info("loadDaily completed successfully");

            }catch(ApplicationException e){
            logger.error("Error during loadDaily");

            emailService.sendErrorMail(e);

        }


    }

}
