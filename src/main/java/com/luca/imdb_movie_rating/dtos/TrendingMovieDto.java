package com.luca.imdb_movie_rating.dtos;

import com.luca.imdb_movie_rating.enums.Genre;

import java.util.List;

public class TrendingMovieDto {

    private Long movieId;

    private Long startRatingId;

    private Long endRatingId;

    private String tConst;

    private Integer numVotesDiff;

    private Integer currentNumVotes;

    private Double avgRatingDiff;

    private Double currentAvgRating;

    private String primaryTitle;

    private String origTitle;

    private Boolean isAdult;

    private Integer runtimeMinutes;

    private List<Genre> genres;

    private String genreList;

    private Integer year;

    private Integer position;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getStartRatingId() {
        return startRatingId;
    }

    public void setStartRatingId(Long startRatingId) {
        this.startRatingId = startRatingId;
    }

    public Long getEndRatingId() {
        return endRatingId;
    }

    public void setEndRatingId(Long endRatingId) {
        this.endRatingId = endRatingId;
    }

    public String gettConst() {
        return tConst;
    }

    public void settConst(String tConst) {
        this.tConst = tConst;
    }

    public Integer getNumVotesDiff() {
        return numVotesDiff;
    }

    public void setNumVotesDiff(Integer numVotesDiff) {
        this.numVotesDiff = numVotesDiff;
    }

    public Integer getCurrentNumVotes() {
        return currentNumVotes;
    }

    public void setCurrentNumVotes(Integer currentNumVotes) {
        this.currentNumVotes = currentNumVotes;
    }

    public Double getAvgRatingDiff() {
        return avgRatingDiff;
    }

    public void setAvgRatingDiff(Double avgRatingDiff) {
        this.avgRatingDiff = avgRatingDiff;
    }

    public Double getCurrentAvgRating() {
        return currentAvgRating;
    }

    public void setCurrentAvgRating(Double currentAvgRating) {
        this.currentAvgRating = currentAvgRating;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }

    public String getOrigTitle() {
        return origTitle;
    }

    public void setOrigTitle(String origTitle) {
        this.origTitle = origTitle;
    }

    public Boolean getAdult() {
        return isAdult;
    }

    public void setAdult(Boolean adult) {
        isAdult = adult;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getGenreList() {
        return genreList;
    }

    public void setGenreList(String genreList) {
        this.genreList = genreList;
    }
}
