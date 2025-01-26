package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.entities.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {

    public void saveNewMovies(List<MovieRow> movieRowList);

    public Map<String, Movie> findAllMoviesMap();

}
