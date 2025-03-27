package com.luca.imdb.movie.reports.entitiy;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Summary extends AbstractSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DAILY_SUMMARY_SEQ")
    @SequenceGenerator(name = "DAILY_SUMMARY_SEQ", sequenceName = "DAILY_SUMMARY_SEQ", allocationSize = 1)
    private Long id;


    @OneToMany(mappedBy="summary", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<GenreSummary> genreSummaryList;

    private LocalDate endDate;

    private LocalDate startDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<GenreSummary> getGenreSummaryList() {
        return genreSummaryList;
    }

    public void setGenreSummaryList(List<GenreSummary> genreSummaryList) {
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
