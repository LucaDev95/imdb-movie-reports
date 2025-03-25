package com.luca.imdb_movie_rating.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatUtils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static DateTimeFormatter csvNameFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private FormatUtils(){}

    public static String formatDate(LocalDate date){
        return date.format(formatter);

    }

    public static String formatCsvDate(LocalDate date){
        return date.format(csvNameFormatter);

    }
}
