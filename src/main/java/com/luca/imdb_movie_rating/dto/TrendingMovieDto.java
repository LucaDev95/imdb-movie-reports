package com.luca.imdb_movie_rating.dto;

import com.luca.imdb_movie_rating.enums.Genre;

import java.util.ArrayList;
import java.util.List;

public class TrendingMovieDto{

    private List<Genre> genreList;

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

    private Integer year;

    private Integer position;

    public TrendingMovieDto(){
        this.genreList=new ArrayList<>();
    }

    public void addGenre(Genre genre){
        this.genreList.add(genre);
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

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
}
