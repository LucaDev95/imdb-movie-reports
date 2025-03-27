package com.luca.imdb.movie.reports.service;

import com.luca.imdb.movie.reports.enums.Genre;

import java.util.Map;

public interface GenreService {

    public Map<Genre,Integer> getGenreMap();
}
