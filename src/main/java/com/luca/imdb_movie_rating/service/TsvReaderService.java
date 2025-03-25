package com.luca.imdb_movie_rating.service;

public interface TsvReaderService {

    public void readTitlesTsv();

    public void readRatingsTsv();

    //public void readTitlesFirstTime();

    public void loadFirstTime();


}
