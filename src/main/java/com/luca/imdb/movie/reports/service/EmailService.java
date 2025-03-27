package com.luca.imdb.movie.reports.service;

import com.luca.imdb.movie.reports.exception.ApplicationException;

public interface EmailService {

    public void sendSummaryMail(String trendingMoviesReport,String summaryReport);

    public void sendErrorMail(ApplicationException applicationException);


}
