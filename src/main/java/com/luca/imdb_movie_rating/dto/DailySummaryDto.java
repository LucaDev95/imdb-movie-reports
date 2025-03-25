package com.luca.imdb_movie_rating.dto;

public class DailySummaryDto {

    private Long numNewVotes;

    private Long totalNumVotes;

    private Double avgRating;

    private Double avgRatingVariation;

    private Long moviesValuated;

    private Double currentVoteDensity;

    private Double totalAvgNumVotes;

    private Long numNewMovies;

    private Long todayNumAdultMovies;

    private Double overallAdultMoviesPerc;

    private Double newMoviesAvgDuration;

    private Double overallAvgRuntimeMinutes;

    public Long getNumNewVotes() {
        return numNewVotes;
    }

    public void setNumNewVotes(Long numNewVotes) {
        this.numNewVotes = numNewVotes;
    }

    public Long getTotalNumVotes() {
        return totalNumVotes;
    }

    public void setTotalNumVotes(Long totalNumVotes) {
        this.totalNumVotes = totalNumVotes;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Double getAvgRatingVariation() {
        return avgRatingVariation;
    }

    public void setAvgRatingVariation(Double avgRatingVariation) {
        this.avgRatingVariation = avgRatingVariation;
    }

    public Long getMoviesValuated() {
        return moviesValuated;
    }

    public void setMoviesValuated(Long moviesValuated) {
        this.moviesValuated = moviesValuated;
    }

    public Double getCurrentVoteDensity() {
        return currentVoteDensity;
    }

    public void setCurrentVoteDensity(Double currentVoteDensity) {
        this.currentVoteDensity = currentVoteDensity;
    }

    public Double getTotalAvgNumVotes() {
        return totalAvgNumVotes;
    }

    public void setTotalAvgNumVotes(Double totalAvgNumVotes) {
        this.totalAvgNumVotes = totalAvgNumVotes;
    }

    public Long getNumNewMovies() {
        return numNewMovies;
    }

    public void setNumNewMovies(Long numNewMovies) {
        this.numNewMovies = numNewMovies;
    }

    public Long getTodayNumAdultMovies() {
        return todayNumAdultMovies;
    }

    public void setTodayNumAdultMovies(Long todayNumAdultMovies) {
        this.todayNumAdultMovies = todayNumAdultMovies;
    }

    public Double getOverallAdultMoviesPerc() {
        return overallAdultMoviesPerc;
    }

    public void setOverallAdultMoviesPerc(Double overallAdultMoviesPerc) {
        this.overallAdultMoviesPerc = overallAdultMoviesPerc;
    }

    public Double getNewMoviesAvgDuration() {
        return newMoviesAvgDuration;
    }

    public void setNewMoviesAvgDuration(Double newMoviesAvgDuration) {
        this.newMoviesAvgDuration = newMoviesAvgDuration;
    }

    public Double getOverallAvgRuntimeMinutes() {
        return overallAvgRuntimeMinutes;
    }

    public void setOverallAvgRuntimeMinutes(Double overallAvgRuntimeMinutes) {
        this.overallAvgRuntimeMinutes = overallAvgRuntimeMinutes;
    }

}
