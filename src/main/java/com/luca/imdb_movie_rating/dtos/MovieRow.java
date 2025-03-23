package com.luca.imdb_movie_rating.dtos;

import com.luca.imdb_movie_rating.enums.Genre;

import java.util.List;

public class MovieRow {

    private String tConst;

    private String primaryTitle;

    private String originalTitle;

    private boolean isAdult;

    private Integer year;

    private Integer runtimeMinutes;

    private List<Genre> genres;

    private RatingRow ratingRow;

    public String gettConst() {
        return tConst;
    }

    public void settConst(String tConst) {
        this.tConst = tConst;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public void setRuntimeMinutes(Integer runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public RatingRow getRatingRow() {
        return ratingRow;
    }

    public void setRatingRow(RatingRow ratingRow) {
        this.ratingRow = ratingRow;
    }
}
