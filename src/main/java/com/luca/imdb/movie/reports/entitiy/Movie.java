package com.luca.imdb.movie.reports.entitiy;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVIE_SEQ")
    @SequenceGenerator(name = "MOVIE_SEQ", sequenceName = "MOVIE_SEQ", allocationSize = 10)
    private Long id;

    @Column(unique = true)
    private String tConst;

    private String primaryTitle;

    private String originalTitle;

    private Boolean isAdult;

    private Integer year;

    private Integer runtimeMinutes;

    @OneToMany(mappedBy="movie", cascade = CascadeType.PERSIST)
    private List<Rating> ratingList;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    List<GenreEntity> genreList;


    @CreationTimestamp
    private LocalDate insertDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getInsertDate() {
        return insertDate;
    }


    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public List<GenreEntity> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<GenreEntity> genreList) {
        this.genreList = genreList;
    }

    public void setInsertDate(LocalDate insertDate) {
        this.insertDate = insertDate;
    }
}
