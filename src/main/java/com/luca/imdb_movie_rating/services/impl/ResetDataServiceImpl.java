package com.luca.imdb_movie_rating.services.impl;
import com.luca.imdb_movie_rating.exceptions.ApplicationException;
import com.luca.imdb_movie_rating.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ResetDataServiceImpl implements ResetDataService {

    private static final Logger logger = LoggerFactory.getLogger(ResetDataServiceImpl.class);

    private final DownloadTsvService downloadService;

    private final ResetTablesService resetTablesService;

    private final TsvReaderService tsvReaderService;

    private final EmailService emailService;

    public ResetDataServiceImpl(DownloadTsvService downloadService, ResetTablesService resetTablesService, TsvReaderService tsvReaderService, EmailService emailService) {
        this.downloadService = downloadService;
        this.resetTablesService=resetTablesService;
        this.tsvReaderService=tsvReaderService;
        this.emailService=emailService;
    }

    @Override
    public void resetData() {


        logger.info("resetData start");

        try {

            logger.info("Truncating all tables");
            resetTablesService.resetTables();

            logger.info("loading all movies and ratings");
            tsvReaderService.loadFirstTime();

            logger.info("resetData completed successfully");

        }catch(ApplicationException e){

            logger.error("Error during resetData",e);

            emailService.sendErrorMail(e);
        }


    }
}
