package com.luca.imdb_movie_rating.entitiy;

import jakarta.persistence.*;

@Entity
public class DailyTrendingMoviesSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DAILY_TRENDING_MOVIES_SEQ")
    @SequenceGenerator(name = "DAILY_TRENDING_MOVIES_SEQ", sequenceName = "DAILY_TRENDING_MOVIES_SEQ", allocationSize = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name="movie_id",nullable = false)
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "rating_start_id")
    private Rating startRatig;

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

    public Rating getStartRatig() {
        return startRatig;
    }

    public void setStartRatig(Rating startRatig) {
        this.startRatig = startRatig;
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
}
