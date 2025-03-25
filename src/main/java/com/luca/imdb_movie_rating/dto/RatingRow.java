package com.luca.imdb_movie_rating.dto;

public record RatingRow(Long movieId,Float averageRating,Integer numVotes) {
}
