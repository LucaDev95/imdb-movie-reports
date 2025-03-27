package com.luca.imdb.movie.reports.entitiy;

import jakarta.persistence.*;

@Entity
public class TrendingMoviesSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRENDING_MOVIES_SEQ")
    @SequenceGenerator(name = "TRENDING_MOVIES_SEQ", sequenceName = "TRENDING_MOVIES_SEQ", allocationSize = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name="movie_id",nullable = false)
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "rating_start_id")
    private Rating startRating;

    @OneToOne
    @JoinColumn(name = "rating_end_id")
    private Rating endRating;

    private Integer position;

    public Long getId() {
        return id;
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

    public Rating getEndRating() {
        return endRating;
    }

    public void setEndRating(Rating endRating) {
        this.endRating = endRating;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Rating getStartRating() {
        return startRating;
    }

    public void setStartRating(Rating startRating) {
        this.startRating = startRating;
    }
}
