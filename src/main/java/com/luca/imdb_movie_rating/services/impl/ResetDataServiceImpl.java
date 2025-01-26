package com.luca.imdb_movie_rating.services.impl;
import com.luca.imdb_movie_rating.dtos.RatingRow;
import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.services.DownloadTsvService;
import com.luca.imdb_movie_rating.services.MovieService;
import com.luca.imdb_movie_rating.services.ResetDataService;
import com.luca.imdb_movie_rating.services.TsvReaderService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Map;

@Service
public class ResetDataServiceImpl implements ResetDataService {

    private final DownloadTsvService downloadService;

    private final MovieService movieService;

    private final TsvReaderService tsvReaderService;

    public ResetDataServiceImpl(DownloadTsvService downloadService,MovieService movieService,TsvReaderService tsvReaderService) {
        this.downloadService = downloadService;
        this.movieService=movieService;
        this.tsvReaderService=tsvReaderService;
    }

    @Override
    @Async
    public void resetData() {
       // downloadService.downloadTsv();
        System.out.println("download completed");

       // movieService.saveNewMovies(null);

        Map<String, RatingRow> ratingMap=this.tsvReaderService.readRatingsTsv(null);
        List<MovieRow> movieRowList= this.tsvReaderService.readTitlesTsv(null,ratingMap);


        movieService.saveNewMovies(movieRowList);



    }
}
