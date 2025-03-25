package com.luca.imdb_movie_rating.util;

import com.luca.imdb_movie_rating.dto.MovieRow;
import com.luca.imdb_movie_rating.enums.Genre;

import java.util.Arrays;
import java.util.List;
import static com.luca.imdb_movie_rating.util.ImdbConstants.*;

public class ParsingUtil {


    public static List<Genre> parseGenreList(String[] movieArr){

        List<Genre> genreList= Arrays.stream(movieArr[GENRES].split(",")).map(Genre::fromString).toList();
        if(genreList.isEmpty()){
            throw new RuntimeException("Genre list cannot be null");
        }
        return genreList;
    }

    public static String parseTitleType(String[] movieArr){
        return movieArr[TILE_TYPE];
    }

    public static Integer parseYear(String[] movieArr){
        return Integer.parseInt(movieArr[START_YEAR]);

    }

    public static boolean parseIsAdult(String[] movieArr){
        return "1".equals(movieArr[IS_ADULT]);
    }

    public static Integer parseRuntimeMinutes(String[] movieArr){
        Integer runtimeMinutes;
        try{
            runtimeMinutes=Integer.parseInt(movieArr[RUNTIME_MINUTES]);
        }catch(NumberFormatException e){
            runtimeMinutes=null;
        }
        return runtimeMinutes;
    }



    public static Float parseAvgrating(String[] ratingArr){
        return Float.parseFloat(ratingArr[AVERAGE_RATING]);
    }

    public static Integer parseNumVotes(String[] ratingArr){
        return Integer.parseInt(ratingArr[NUM_VOTES]);

    }

    public static String parseTConst(String[] arr){
        return arr[T_CONST];
    }

    public static String parsePrimaryTitle(String[] movieArr){
        return movieArr[PRIMARY_TITLE];
    }

    public static String parseOriginalTitle(String[] movieArr){
        return movieArr[ORIGINAL_TITLE];
    }

    public static MovieRow parseOtherFields(String[] movieArr,MovieRow movieRow){
        movieRow.setAdult(parseIsAdult(movieArr));
        movieRow.setGenres(parseGenreList(movieArr));
        movieRow.setOriginalTitle(parseOriginalTitle(movieArr));
        movieRow.setPrimaryTitle(parsePrimaryTitle(movieArr));
        movieRow.setRuntimeMinutes(parseRuntimeMinutes(movieArr));
        return movieRow;
    }


}
