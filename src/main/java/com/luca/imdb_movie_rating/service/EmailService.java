package com.luca.imdb_movie_rating.service;

import com.luca.imdb_movie_rating.exception.ApplicationException;

public interface EmailService {

    public void sendSummaryMail(String trendingMoviesReport,String summaryReport);

    public void sendErrorMail(ApplicationException applicationException);


}
