package com.luca.imdb_movie_rating.dto;

import com.luca.imdb_movie_rating.enums.Genre;

public class GenreSummaryDoubleResult extends GenreSummaryResult {

    private Double value;

    public GenreSummaryDoubleResult(Genre genre, Double value) {
        super(genre);
        this.value=value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
