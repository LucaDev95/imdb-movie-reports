package com.luca.imdb.movie.reports.repository;

import com.luca.imdb.movie.reports.entitiy.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface SummaryRepository extends JpaRepository<Summary,Long> {

    @Query("select sum(r.numVotes) from Rating r where r.insertDate=:valuationDate")
    public Long getSumNumVotesUntilDate(@Param("valuationDate")LocalDate localDate);

    @Query("select avg(r.averageRating) from Rating r where r.insertDate=:valuationDate")
    public Double getAvgRatingByDate(@Param("valuationDate") LocalDate localDate);


    @Query("select avg(r.averageRating) from Rating r")
    public Double getTotalAvgRating();

    @Query("select count(mov.id) from Movie mov")
    public Long getAnalyzedMovies();

    @Query("select count(mov.id) from Movie mov where mov.insertDate>=:startDate")
    public Long getNewMovies(@Param("startDate") LocalDate startDate);

    @Query("select count(mov.id) from Movie mov where mov.isAdult=true")
    public Long getTotalNumAdultMovies();

    @Query("select avg(mov.runtimeMinutes) from Movie mov where mov.insertDate>=:startDate and mov.runtimeMinutes is not null")
    public Double getAvgRuntimeMinutesByDate(@Param("startDate") LocalDate startDate);

    @Query("select avg(mov.runtimeMinutes) from Movie mov where mov.runtimeMinutes is not null")
    public Double getAvgTotalRuntimeMinutes();

    @Modifying
    @Query("delete from Summary d where d.endDate=:date")
    public void deleteByEndRatingDate(@Param("date") LocalDate date);

    @Modifying
    @Query(value = "truncate summary CASCADE",nativeQuery = true)
    public void truncate();

    public Optional<Summary> findByEndDate(LocalDate endDate);


}
