package com.luca.imdb_movie_rating.service;

import com.luca.imdb_movie_rating.dto.RatingRow;

import java.util.List;

public interface RatingService {

    public void saveRatingRows(List<RatingRow> ratingRows);

    public void deleteCurrentRatings();


}
