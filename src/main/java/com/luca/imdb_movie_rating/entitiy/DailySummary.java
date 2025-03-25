package com.luca.imdb_movie_rating.entitiy;

import jakarta.persistence.*;

@Entity
public class DailySummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DAILY_SUMMARY_SEQ")
    @SequenceGenerator(name = "DAILY_SUMMARY_SEQ", sequenceName = "DAILY_SUMMARY_SEQ", allocationSize = 1)
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
