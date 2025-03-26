package com.luca.imdb_movie_rating.dto;
import com.luca.imdb_movie_rating.enums.Genre;
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
