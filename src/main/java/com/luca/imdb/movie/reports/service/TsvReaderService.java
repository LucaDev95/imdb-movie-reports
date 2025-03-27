package com.luca.imdb.movie.reports.service;

public interface TsvReaderService {

    public void readTitlesTsv();

    public void readRatingsTsv();

    //public void readTitlesFirstTime();

    public void loadFirstTime();


}
