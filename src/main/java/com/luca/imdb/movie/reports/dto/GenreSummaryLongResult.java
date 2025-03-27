package com.luca.imdb.movie.reports.dto;

import com.luca.imdb.movie.reports.enums.Genre;

public class GenreSummaryLongResult extends GenreSummaryResult{

    private Long value;

    public GenreSummaryLongResult( Long value,Genre genre) {
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
