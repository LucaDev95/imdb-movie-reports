package com.luca.imdb.movie.reports.service;

import com.luca.imdb.movie.reports.dto.MovieRow;
import com.luca.imdb.movie.reports.enums.Genre;

import java.util.List;
import java.util.Map;

public interface MovieService {

    public void saveNewMovies(List<MovieRow> movieRowList, Map<Genre,Integer> genreMap);

    public void deleteCurrentDate();

    public Map<String,Long> getMovieNextTConstMap(String tConst);

}
