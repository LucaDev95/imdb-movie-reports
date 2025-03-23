package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.exceptions.ApplicationException;
import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendSummaryMail(String trendingMoviesReport,String summaryReport);

    public void sendErrorMail(ApplicationException applicationException);


}
