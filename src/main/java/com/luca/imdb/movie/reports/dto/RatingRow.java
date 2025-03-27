package com.luca.imdb.movie.reports.dto;

public record RatingRow(Long movieId,Float averageRating,Integer numVotes) {
}
