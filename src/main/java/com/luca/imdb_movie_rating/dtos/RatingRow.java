package com.luca.imdb_movie_rating.dtos;

public record RatingRow(Long movieId,Float averageRating,Integer numVotes) {
}
