package com.luca.imdb_movie_rating.entitiy;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class DailySummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DAILY_SUMMARY_SEQ")
    @SequenceGenerator(name = "DAILY_SUMMARY_SEQ", sequenceName = "DAILY_SUMMARY_SEQ", allocationSize = 1)
    private Long id;


    @OneToMany(mappedBy="dailySummary", cascade = CascadeType.PERSIST)
    private List<DailyGenreSummary> genreSummaryList;

    private LocalDate endDate;

    private LocalDate startDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DailyGenreSummary> getGenreSummaryList() {
        return genreSummaryList;
    }

    public void setGenreSummaryList(List<DailyGenreSummary> genreSummaryList) {
        this.genreSummaryList = genreSummaryList;
    }


    public LocalDate getEndDate() {
        return endDate;
    }


    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public LocalDate getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
