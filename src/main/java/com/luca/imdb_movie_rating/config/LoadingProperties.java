package com.luca.imdb_movie_rating.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="loader")
public class LoadingProperties {

    private Integer tConstSelectSize;

    private Integer movieInsertSize;

    private Integer ratingInsertSize;

    private Integer maxPreviousYears;

    private Integer maxRatingDays;


    public LoadingProperties(Integer tConstSelectSize, Integer movieInsertSize, Integer ratingInsertSize, Integer maxPreviousYears, Integer maxRatingDays) {
        this.tConstSelectSize = tConstSelectSize;
        this.movieInsertSize = movieInsertSize;
        this.ratingInsertSize = ratingInsertSize;
        this.maxPreviousYears = maxPreviousYears;
        this.maxRatingDays = maxRatingDays;
    }

    public Integer getRatingInsertSize() {
        return ratingInsertSize;
    }

    public Integer getMaxPreviousYears() {
        return maxPreviousYears;
    }

    public Integer getMaxRatingDays() {
        return maxRatingDays;
    }

    public Integer getMovieInsertSize() {
        return movieInsertSize;
    }

    public Integer gettConstSelectSize() {
        return tConstSelectSize;
    }
}
