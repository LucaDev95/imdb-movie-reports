package com.luca.imdb_movie_rating.exception;

public class ReportException extends ApplicationException {

    public ReportException(String message,Throwable cause){
        super(message,cause);
    }

    public ReportException(String message){
        super(message);
    }
}
