package com.luca.imdb.movie.reports.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="imdb")
public class ImdbProperties {

    private String baseUrl;

    private String basicsGz;

    private String ratingsGz;

    public ImdbProperties(String baseUrl, String basicsGz, String ratingsGz) {
        this.baseUrl = baseUrl;
        this.basicsGz = basicsGz;
        this.ratingsGz = ratingsGz;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBasicsGz() {
        return baseUrl+basicsGz;
    }


    public String getRatingsGz() {
        return baseUrl+ratingsGz;
    }


}
