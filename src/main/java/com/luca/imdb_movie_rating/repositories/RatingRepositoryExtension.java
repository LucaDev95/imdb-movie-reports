package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.dtos.RatingResult;

import java.util.List;

public interface RatingRepositoryExtension {


    public List<RatingResult> findRatingResultList();

}
