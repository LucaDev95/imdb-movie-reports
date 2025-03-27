package com.luca.imdb.movie.reports.runner;

import com.luca.imdb.movie.reports.service.MainService;
import com.luca.imdb.movie.reports.service.ResetDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner  implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);

    private final ResetDataService resetDataService;

    private final MainService loadDailyService;

    public JobRunner(ResetDataService resetDataService, MainService loadDailyService){
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
