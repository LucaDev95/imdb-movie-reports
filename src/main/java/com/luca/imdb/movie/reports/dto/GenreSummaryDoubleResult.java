package com.luca.imdb.movie.reports.dto;

import com.luca.imdb.movie.reports.enums.Genre;

public class GenreSummaryDoubleResult extends GenreSummaryResult {

    private Double value;

    public GenreSummaryDoubleResult(Double value,Genre genre) {
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
