package com.luca.imdb.movie.reports.entitiy;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractSummary {

    @Column(nullable = false)
    private Long numNewVotes;

    @Column(nullable = false)
    private Long numTotalVotes;

    @Column(nullable = false)
    private Double avgRating;

    @Column(nullable = false)
    private Double avgRatingVariation;

    @Column(nullable = false)
    private Long numMoviesAnalyzed;

    @Column(nullable = false)
    private Double currentVoteDensity;

    @Column(nullable = false)
    private Double totalAvgNumVotes;

    @Column(nullable = false)
    private Long numNewMovies;

    @Column(nullable = false)
    private Long numTotalAdultMovies;

    @Column(nullable = false)
    private Double totalAdultMoviesPerc;

    @Column(nullable = false)
    private Double newMoviesAvgDuration;

    @Column(nullable = false)
    private Double totalAvgDuration;

    public Long getNumNewVotes() {
        return numNewVotes;
    }

    public void setNumNewVotes(Long numNewVotes) {
        this.numNewVotes = numNewVotes;
    }

    public Long getNumTotalVotes() {
        return numTotalVotes;
    }

    public void setNumTotalVotes(Long numTotalVotes) {
        this.numTotalVotes = numTotalVotes;
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

    public Long getNumMoviesAnalyzed() {
        return numMoviesAnalyzed;
    }

    public void setNumMoviesAnalyzed(Long numMoviesAnalyzed) {
        this.numMoviesAnalyzed = numMoviesAnalyzed;
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

    public Long getNumTotalAdultMovies() {
        return numTotalAdultMovies;
    }

    public void setNumTotalAdultMovies(Long numTotalAdultMovies) {
        this.numTotalAdultMovies = numTotalAdultMovies;
    }

    public Double getNewMoviesAvgDuration() {
        return newMoviesAvgDuration;
    }

    public void setNewMoviesAvgDuration(Double newMoviesAvgDuration) {
        this.newMoviesAvgDuration = newMoviesAvgDuration;
    }

    public Double getTotalAvgDuration() {
        return totalAvgDuration;
    }

    public void setTotalAvgDuration(Double totalAvgDuration) {
        this.totalAvgDuration = totalAvgDuration;
    }
}
