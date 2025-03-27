package com.luca.imdb.movie.reports.service.impl;
import com.luca.imdb.movie.reports.exception.ApplicationException;
import com.luca.imdb.movie.reports.service.EmailService;
import com.luca.imdb.movie.reports.service.ResetTablesService;
import com.luca.imdb.movie.reports.service.TsvReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.luca.imdb.movie.reports.service.ResetDataService;

@Service
public class ResetDataServiceImpl implements ResetDataService {

    private static final Logger logger = LoggerFactory.getLogger(ResetDataServiceImpl.class);

    private final ResetTablesService resetTablesService;

    private final TsvReaderService tsvReaderService;

    private final EmailService emailService;

    public ResetDataServiceImpl( ResetTablesService resetTablesService, TsvReaderService tsvReaderService, EmailService emailService) {
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
        }catch(Exception e){
            logger.error("Unhandled exception ",e);
        }


    }
}
