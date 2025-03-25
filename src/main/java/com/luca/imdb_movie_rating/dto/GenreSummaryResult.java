package com.luca.imdb_movie_rating.dto;

import com.luca.imdb_movie_rating.enums.Genre;

public abstract class GenreSummaryResult {

    private Genre genre;

    public GenreSummaryResult(Genre genre){
        this.genre=genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
