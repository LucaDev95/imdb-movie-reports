package com.luca.imdb_movie_rating.dtos;

public class RatingResult {

    private String tConst;

    private Integer numVotesDiff;

    private Integer currentNumVotes;

    private Float avgRatingDiff;

    private Float currentAvgRating;

    private String primaryTitle;

    private String origTitle;

    private Boolean isAdult;

    private Integer runtimeMinutes;

    private String genres;

    private Integer year;

    private Integer position;

    public RatingResult(String tConst, String primaryTitle, String origTitle, Integer numVotesDiff, Integer currentNumVotes, Float avgRatingDiff, Float currentAvgRating, Boolean isAdult, String genres, Integer year, Integer runtimeMinutes) {
        this.tConst = tConst;
        this.numVotesDiff = numVotesDiff;
        this.currentNumVotes = currentNumVotes;
        this.avgRatingDiff = avgRatingDiff;
        this.currentAvgRating = currentAvgRating;
        this.primaryTitle = primaryTitle;
        this.origTitle = origTitle;
        this.isAdult = isAdult;
        this.runtimeMinutes = runtimeMinutes;
        this.genres = genres;
        this.year = year;
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

    public Float getAvgRatingDiff() {
        return avgRatingDiff;
    }

    public void setAvgRatingDiff(Float avgRatingDiff) {
        this.avgRatingDiff = avgRatingDiff;
    }

    public Float getCurrentAvgRating() {
        return currentAvgRating;
    }

    public void setCurrentAvgRating(Float currentAvgRating) {
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
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

    @Override
    public String toString() {
        return "RatingResult{" +
                "tConst='" + tConst + '\'' +
                ", numVotesDiff=" + numVotesDiff +
                ", currentNumVotes=" + currentNumVotes +
                ", avgRatingDiff=" + avgRatingDiff +
                ", currentAvgRating=" + currentAvgRating +
                ", primaryTitle='" + primaryTitle + '\'' +
                ", origTitle='" + origTitle + '\'' +
                ", isAdult=" + isAdult +
                ", runtimeMinutes=" + runtimeMinutes +
                ", genres='" + genres + '\'' +
                ", year=" + year +
                ", position=" + position +
                '}';
    }
}
