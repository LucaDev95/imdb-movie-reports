package com.luca.imdb.movie.reports.service;

import com.luca.imdb.movie.reports.dto.RatingRow;

import java.util.List;

public interface RatingService {

    public void saveRatingRows(List<RatingRow> ratingRows);

    public void deleteCurrentRatings();


}
