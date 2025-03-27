package com.luca.imdb.movie.reports.entitiy;

import jakarta.persistence.*;

@Entity
public class GenreSummary extends AbstractSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GENRE_SUMMARY_SEQ")
    @SequenceGenerator(name = "GENRE_SUMMARY_SEQ", sequenceName = "GENRE_SUMMARY_SEQ", allocationSize = 5)
    private Long id;

    @ManyToOne
    @JoinColumn(name="genre_id",nullable = false)
    private GenreEntity genre;

    @ManyToOne
    @JoinColumn(name="summary_id")
    private Summary summary;

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
