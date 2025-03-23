package com.luca.imdb_movie_rating.services;

import com.luca.imdb_movie_rating.enums.Genre;

import java.util.Map;

public interface GenreService {

    public Map<Genre,Integer> getGenreMap();
}
