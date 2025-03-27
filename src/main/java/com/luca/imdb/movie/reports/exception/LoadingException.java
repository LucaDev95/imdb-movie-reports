package com.luca.imdb.movie.reports.exception;

public class LoadingException extends ApplicationException{

    public LoadingException(String message,Throwable cause){
        super(message,cause);
    }

    public LoadingException(String message){
        super(message);
    }


}
