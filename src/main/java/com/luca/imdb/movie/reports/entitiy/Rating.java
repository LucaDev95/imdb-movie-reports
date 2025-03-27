package com.luca.imdb.movie.reports.entitiy;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;


@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RATING_SEQ")
    @SequenceGenerator(name = "RATING_SEQ", sequenceName = "RATING_SEQ", allocationSize = 10)
    private Long id;

    private Float averageRating;

    private Integer numVotes;

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;

    @CreationTimestamp
    private LocalDate insertDate;

    public Long getId() {
        return id;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDate getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDate insertDate) {
        this.insertDate = insertDate;
    }
}
