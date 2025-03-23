package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.dtos.RatingRow;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TsvReaderService {

    public void readTitlesTsv();

    public void readRatingsTsv();

    //public void readTitlesFirstTime();

    public void loadFirstTime();


}
