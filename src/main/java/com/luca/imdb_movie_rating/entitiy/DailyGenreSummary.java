package com.luca.imdb_movie_rating.entitiy;

import jakarta.persistence.*;

@Entity
public class DailyGenreSummary extends Summary{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DAILY_GENRE_SUMMARY_SEQ")
    @SequenceGenerator(name = "DAILY_GENRE_SUMMARY_SEQ", sequenceName = "DAILY_GENRE_SUMMARY_SEQ", allocationSize = 5)
    private Long id;

    @ManyToOne
    @JoinColumn(name="genre_id",nullable = false)
    private GenreEntity genre;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenreEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }
}
