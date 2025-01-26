package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.dtos.RatingRow;
import com.luca.imdb_movie_rating.entities.Movie;
import com.luca.imdb_movie_rating.entities.Rating;
import com.luca.imdb_movie_rating.services.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class LoadDailyServiceImpl implements  LoadDailyService{

    private final DownloadTsvService downloadService;

    private final MovieService movieService;

    private final TsvReaderService tsvReaderService;

    private final RatingService ratingService;

    private final ReportService reportService;

    public LoadDailyServiceImpl(DownloadTsvService downloadService,MovieService movieService,TsvReaderService tsvReaderService,RatingService ratingService,ReportService reportService){
        this.downloadService=downloadService;
        this.movieService=movieService;
        this.tsvReaderService=tsvReaderService;
        this.ratingService=ratingService;
        this.reportService= reportService;
    }

    @Override
    @Async
    public void loadDaily(){
        //downloadService.downloadTsv();


        Map<String, RatingRow> ratingMap=tsvReaderService.readRatingsTsv(null);

        Map<String,Movie> oldMoviesMap=movieService.findAllMoviesMap();

        Map<String,RatingRow> newMoviesRatingMap=processOldMoviesRating(ratingMap,oldMoviesMap);

        System.out.println("new movies found");
        System.out.println(newMoviesRatingMap.size());

        List<MovieRow> movieRowList= this.tsvReaderService.readTitlesTsv(null,newMoviesRatingMap);

        movieService.saveNewMovies(movieRowList);

        reportService.generateDailyReport();



    }

    private Map<String,RatingRow> processOldMoviesRating(Map<String,RatingRow> ratingMap,Map<String, Movie> movieMap){
        Map<String,RatingRow> newMoviesRatings=new HashMap<>();

        List<Rating> toPersist=new ArrayList<>();

        for(String tconst:ratingMap.keySet()){

            Movie foundMovie= movieMap.get(tconst);

            RatingRow row=ratingMap.get(tconst);

            if(foundMovie!=null){

                Rating rating= new Rating();
                rating.setAverageRating(row.averageRating());
                rating.setNumVotes(row.numVotes());
                rating.setMovie(foundMovie);

                toPersist.add(rating);


            }else{
                newMoviesRatings.put(tconst,row);
            }

        }

        ratingService.loadRatings(toPersist);

        return newMoviesRatings;
    }

}
