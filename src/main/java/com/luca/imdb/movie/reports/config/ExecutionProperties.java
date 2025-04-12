package com.luca.imdb.movie.reports.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;

@ConfigurationProperties(prefix = "execution")
public class ExecutionProperties {

    private Integer tConstSelectSize;

    private Integer movieInsertSize;

    private Integer ratingInsertSize;

    private Integer maxPreviousYears;

    private Integer maxRatingDays;

    private String recipient;

    private Integer summaryIntervalDays;

    private LocalDate currentDate;

    private LocalDate startDate;

    private Integer timeoutSeconds;


    public ExecutionProperties(Integer tConstSelectSize, Integer movieInsertSize, Integer ratingInsertSize, Integer maxPreviousYears, Integer maxRatingDays, String recipient, Integer summaryIntervalDays, Integer timeoutSeconds) {
        this.tConstSelectSize = tConstSelectSize;
        this.movieInsertSize = movieInsertSize;
        this.ratingInsertSize = ratingInsertSize;
        this.maxPreviousYears = maxPreviousYears;
        this.maxRatingDays = maxRatingDays;
        this.recipient = recipient;
        this.summaryIntervalDays = summaryIntervalDays;
        this.currentDate = LocalDate.now();
        this.startDate = currentDate.minusDays(summaryIntervalDays);
        this.timeoutSeconds = timeoutSeconds;
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

    public String getRecipient() {
        return recipient;
    }

    public Integer getSummaryIntervalDays() {
        return summaryIntervalDays;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }
}
