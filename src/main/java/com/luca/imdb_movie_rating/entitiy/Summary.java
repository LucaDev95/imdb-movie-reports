package com.luca.imdb_movie_rating.entitiy;


import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Summary {

    private Long todayNumVotes;

    private Long totalNumVotes;

    private Double avgRating;

    private Double avgRatingVariation;

    private Long moviesValuated;

    private Double currentVoteDensity;

    // ntotalNumVotes / moviesValuated
    private Double totalAvgNumVotes;

    private Long todayNumMovies;

    private Long todayNumAdultMovies;

    private Long overallNumAdultMovies;

    private Double todayAdultMoviesPerc;

    //overallNumAdultMovies / moviesValuated
    private Double overallAdultMoviesPerc;

    private Double todayAvgRuntimeMinutes;

    private Double overallAvgRuntimeMinutes;

    private LocalDate valuationDate;

    private LocalDate valuationStartDate;

    public Long getTodayNumVotes() {
        return todayNumVotes;
    }

    public void setTodayNumVotes(Long todayNumVotes) {
        this.todayNumVotes = todayNumVotes;
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

    public Long getMoviesValuated() {
        return moviesValuated;
    }

    public void setMoviesValuated(Long moviesValuated) {
        this.moviesValuated = moviesValuated;
    }


    public Double getTotalAvgNumVotes() {
        return totalAvgNumVotes;
    }

    public void setTotalAvgNumVotes(Double totalAvgNumVotes) {
        this.totalAvgNumVotes = totalAvgNumVotes;
    }


    public Double getAvgRatingVariation() {
        return avgRatingVariation;
    }

    public void setAvgRatingVariation(Double avgRatingVariation) {
        this.avgRatingVariation = avgRatingVariation;
    }

    public Double getTodayAdultMoviesPerc() {
        return todayAdultMoviesPerc;
    }

    public void setTodayAdultMoviesPerc(Double todayAdultMoviesPerc) {
        this.todayAdultMoviesPerc = todayAdultMoviesPerc;
    }

    public Long getTodayNumMovies() {
        return todayNumMovies;
    }

    public void setTodayNumMovies(Long todayNumMovies) {
        this.todayNumMovies = todayNumMovies;
    }

    public Long getTodayNumAdultMovies() {
        return todayNumAdultMovies;
    }

    public void setTodayNumAdultMovies(Long todayNumAdultMovies) {
        this.todayNumAdultMovies = todayNumAdultMovies;
    }

    public Long getOverallNumAdultMovies() {
        return overallNumAdultMovies;
    }

    public void setOverallNumAdultMovies(Long overallNumAdultMovies) {
        this.overallNumAdultMovies = overallNumAdultMovies;
    }

    public Double getOverallAdultMoviesPerc() {
        return overallAdultMoviesPerc;
    }

    public void setOverallAdultMoviesPerc(Double overallAdultMoviesPerc) {
        this.overallAdultMoviesPerc = overallAdultMoviesPerc;
    }

    public Double getTodayAvgRuntimeMinutes() {
        return todayAvgRuntimeMinutes;
    }

    public void setTodayAvgRuntimeMinutes(Double todayAvgRuntimeMinutes) {
        this.todayAvgRuntimeMinutes = todayAvgRuntimeMinutes;
    }

    public Double getOverallAvgRuntimeMinutes() {
        return overallAvgRuntimeMinutes;
    }

    public void setOverallAvgRuntimeMinutes(Double overallAvgRuntimeMinutes) {
        this.overallAvgRuntimeMinutes = overallAvgRuntimeMinutes;
    }

    public LocalDate getValuationDate() {
        return valuationDate;
    }

    public void setValuationDate(LocalDate valuationDate) {
        this.valuationDate = valuationDate;
    }

    public LocalDate getValuationStartDate() {
        return valuationStartDate;
    }

    public void setValuationStartDate(LocalDate valuationStartDate) {
        this.valuationStartDate = valuationStartDate;
    }

    public Double getCurrentVoteDensity() {
        return currentVoteDensity;
    }

    public void setCurrentVoteDensity(Double currentVoteDensity) {
        this.currentVoteDensity = currentVoteDensity;
    }
}
