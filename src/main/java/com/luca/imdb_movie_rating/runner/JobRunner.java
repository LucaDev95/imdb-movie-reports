package com.luca.imdb_movie_rating.runner;

import com.luca.imdb_movie_rating.service.LoadDailyService;
import com.luca.imdb_movie_rating.service.ResetDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner  implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);

    private final ResetDataService resetDataService;

    private final LoadDailyService loadDailyService;

    public JobRunner(ResetDataService resetDataService,LoadDailyService loadDailyService){
        this.resetDataService=resetDataService;
        this.loadDailyService=loadDailyService;
    }


    @Override
    public void run(String... args) throws Exception {

        if(args.length==0){

            logger.info("No parameters found launching LoadDailyService");

            loadDailyService.loadDaily();
        }else if ("RESET".equals(args[0])){
            logger.info("Parameter {} found, launching ResetDataService",args[0]);
            resetDataService.resetData();
        }else{
            logger.error("Invalid argument {}",args[0]);
            throw new RuntimeException("Invalid argument "+args[0]);
        }
    }
}
