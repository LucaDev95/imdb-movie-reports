package com.luca.imdb.movie.reports.runner;

import com.luca.imdb.movie.reports.service.MainService;
import com.luca.imdb.movie.reports.service.MovieService;
import com.luca.imdb.movie.reports.service.ResetDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component

public class JobRunner  implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);

    private final ResetDataService resetDataService;

    private final MainService loadDailyService;

    private final MovieService movieService;

    public JobRunner(@Lazy ResetDataService resetDataService, MainService loadDailyService, MovieService movieService){
        this.resetDataService=resetDataService;
        this.loadDailyService=loadDailyService;
        this.movieService=movieService;
    }


    @Override
    public void run(String... args) throws Exception {

        long savedMovies=movieService.countMovies();

        if(savedMovies>0){

            logger.info("Movie table is not empty. Launch LoadDaily");

            loadDailyService.loadDaily();
        }else {
            logger.info("Movie table is empty, launch ResetData");
            resetDataService.resetData();
        }
    }
}
