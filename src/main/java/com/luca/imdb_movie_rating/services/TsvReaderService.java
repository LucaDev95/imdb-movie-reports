package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.dtos.RatingRow;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TsvReaderService {

    public List<MovieRow> readTitlesTsv(String tsvPath, Map<String,RatingRow> ratingMap);

    public Map<String,RatingRow> readRatingsTsv(String tsvPath);
}
