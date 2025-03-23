package com.luca.imdb_movie_rating.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="summary")
public class SummaryProperties {

    private String recipient;

    private Integer summaryIntervalDays;

    public SummaryProperties(String recipient, Integer summaryIntervalDays) {
        this.recipient = recipient;
        this.summaryIntervalDays = summaryIntervalDays;
    }

    public String getRecipient() {
        return recipient;
    }

    public Integer getSummaryIntervalDays() {
        return summaryIntervalDays;
    }
}
