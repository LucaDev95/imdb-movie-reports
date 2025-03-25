package com.luca.imdb_movie_rating.service;

import com.luca.imdb_movie_rating.dto.MovieRow;
import com.luca.imdb_movie_rating.enums.Genre;

import java.util.List;
import java.util.Map;

public interface MovieService {

    public void saveNewMovies(List<MovieRow> movieRowList, Map<Genre,Integer> genreMap);

    public void deleteCurrentDate();

    public Map<String,Long> getMovieNextTConstMap(String tConst);

}
