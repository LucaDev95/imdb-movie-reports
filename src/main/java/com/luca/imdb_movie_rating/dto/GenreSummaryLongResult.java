package com.luca.imdb_movie_rating.dto;

import com.luca.imdb_movie_rating.enums.Genre;

public class GenreSummaryLongResult extends GenreSummaryResult{

    private Long value;

    public GenreSummaryLongResult(Genre genre,Long value) {
        super(genre);
        this.value=value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
