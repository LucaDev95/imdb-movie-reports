package com.luca.imdb.movie.reports.dto;
import com.luca.imdb.movie.reports.enums.Genre;

public class DailySummaryGenreDto extends DailySummaryDto {

    private Genre genre;

    public DailySummaryGenreDto(Genre genre){
        this.genre=genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
