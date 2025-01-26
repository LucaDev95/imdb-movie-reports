package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.dtos.RatingResult;
import com.luca.imdb_movie_rating.entities.Rating;

import java.util.List;

public interface RatingService {

    public void loadRatings(List<Rating> ratingList);

    public List<RatingResult> loadRatingResult();
}
