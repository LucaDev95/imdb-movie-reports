package com.luca.imdb_movie_rating.exceptions;

public class LoadingException extends ApplicationException{

    public LoadingException(String message,Throwable cause){
        super(message,cause);
    }

    public LoadingException(String message){
        super(message);
    }


}
