package com.luca.imdb_movie_rating.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVIE_SEQ")
    @SequenceGenerator(name = "MOVIE_SEQ", sequenceName = "MOVIE_SEQ", allocationSize = 10)
    private Long id;

    @Column(nullable = false,unique = true)
    private String tConst;

    private String primaryTitle;

    private String originalTitle;

    private Boolean isAdult;

    private Integer year;

    private Integer runtimeMinutes;

    private String genres;

    @CreationTimestamp
    private LocalDate insertDate;

    @OneToMany(targetEntity = Rating.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "movie")
    private List<Rating> ratingList;

    public Long getId() {
        return id;
    }


    public String gettConst() {
        return tConst;
    }

    public void settConst(String tConst) {
        this.tConst = tConst;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Boolean getAdult() {
        return isAdult;
    }

    public void setAdult(Boolean adult) {
        isAdult = adult;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public LocalDate getInsertDate() {
        return insertDate;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public void addRating(Rating r){
        if(this.ratingList==null){
            this.ratingList=new ArrayList<>();
        }

        this.ratingList.add(r);
    }
}
