package com.luca.imdb_movie_rating.exceptions;

import com.luca.imdb_movie_rating.utils.ErrorUtils;

public abstract class ApplicationException extends RuntimeException{

    private final String mailText;

    public ApplicationException(String message,Throwable cause){

        super(message,cause);
        this.mailText=message+"\n"+ ErrorUtils.stackTraceToString(cause);
    }

    public ApplicationException(String message){
        super(message);
        this.mailText=message;
    }


    public String getMailText(){
        return mailText;
    }
}
