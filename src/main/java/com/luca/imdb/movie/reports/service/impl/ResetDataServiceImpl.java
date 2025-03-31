package com.luca.imdb.movie.reports.service.impl;
import com.luca.imdb.movie.reports.exception.ApplicationException;
import com.luca.imdb.movie.reports.service.EmailService;
import com.luca.imdb.movie.reports.service.ClearUpTablesService;
import com.luca.imdb.movie.reports.service.TsvLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.luca.imdb.movie.reports.service.ResetDataService;

@Service
public class ResetDataServiceImpl implements ResetDataService {

    private static final Logger logger = LoggerFactory.getLogger(ResetDataServiceImpl.class);

    private final ClearUpTablesService resetTablesService;

    private final TsvLoaderService tsvLoaderService;

    private final EmailService emailService;

    public ResetDataServiceImpl(ClearUpTablesService resetTablesService, TsvLoaderService tsvLoaderService, EmailService emailService) {
        this.resetTablesService=resetTablesService;
        this.tsvLoaderService=tsvLoaderService;
        this.emailService=emailService;
    }

    @Override
    public void resetData() {


        logger.info("resetData start");

        try {

            logger.info("Truncating all tables");
            resetTablesService.resetTables();

            logger.info("loading all movies and ratings");
            tsvLoaderService.reload();

            logger.info("resetData completed successfully");

        }catch(ApplicationException e){

            logger.error("Error during resetData",e);

            emailService.sendErrorMail(e);
        }catch(Exception e){
            logger.error("Unhandled exception ",e);
        }


    }
}
