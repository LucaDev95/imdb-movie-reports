package com.luca.imdb_movie_rating.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorUtils {

    private ErrorUtils(){}

    public static String stackTraceToString(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return pw.toString();

    }
}
