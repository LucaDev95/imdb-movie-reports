package com.luca.imdb_movie_rating.dto;

public class DailySummaryDto {

    private Long numNewVotes;

    private Long totalNumVotes;

    private Double avgRating;

    private Double avgRatingVariation;

    private Long numMoviesAnalyzed;

    private Double currentVoteDensity;

    private Double totalAvgNumVotes;

    private Long numNewMovies;

    private Long numTotalAdultMovies;

    private Double totalAdultMoviesPerc;

    private Double newMoviesAvgDuration;
    private Double totalAvgDuration;

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


    public Double getTotalAdultMoviesPerc() {
        return totalAdultMoviesPerc;
    }

    public void setTotalAdultMoviesPerc(Double totalAdultMoviesPerc) {
        this.totalAdultMoviesPerc = totalAdultMoviesPerc;
    }

    public Double getNewMoviesAvgDuration() {
        return newMoviesAvgDuration;
    }

    public void setNewMoviesAvgDuration(Double newMoviesAvgDuration) {
        this.newMoviesAvgDuration = newMoviesAvgDuration;
    }

    public Long getNumMoviesAnalyzed() {
        return numMoviesAnalyzed;
    }

    public void setNumMoviesAnalyzed(Long numMoviesAnalyzed) {
        this.numMoviesAnalyzed = numMoviesAnalyzed;
    }

    public Long getNumTotalAdultMovies() {
        return numTotalAdultMovies;
    }

    public void setNumTotalAdultMovies(Long numTotalAdultMovies) {
        this.numTotalAdultMovies = numTotalAdultMovies;
    }

    public Double getTotalAvgDuration() {
        return totalAvgDuration;
    }

    public void setTotalAvgDuration(Double totalAvgDuration) {
        this.totalAvgDuration = totalAvgDuration;
    }
}
