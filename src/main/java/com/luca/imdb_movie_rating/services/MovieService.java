package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.entities.Movie;
import com.luca.imdb_movie_rating.enums.Genre;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MovieService {

    public void saveNewMovies(List<MovieRow> movieRowList, Map<Genre,Integer> genreMap);

    public void deleteCurrentDate();

    public Map<String,Long> getMovieNextTConstMap(String tConst);

}
