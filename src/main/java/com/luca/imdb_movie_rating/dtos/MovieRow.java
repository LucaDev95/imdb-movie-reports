package com.luca.imdb_movie_rating.dtos;

public record MovieRow(String tConst,String primaryTitle,String originalTitle,boolean isAdult,
    Integer year,Integer runtimeMinutes,String genres,RatingRow ratingRow) {

}
