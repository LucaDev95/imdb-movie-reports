package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.dtos.TrendingMovieDto;
import com.luca.imdb_movie_rating.dtos.RatingRow;
import com.luca.imdb_movie_rating.entities.Rating;

import java.util.List;

public interface RatingService {

    public void saveRatingRows(List<RatingRow> ratingRows);

    public void deleteCurrentRatings();


}
