package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.entities.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface DailySummaryRepository extends JpaRepository<DailySummary,Long> {

    @Query("select sum(r.numVotes) from Rating r where r.insertDate=:valuationDate")
    public Long getSumNumVotesUntilDate(@Param("valuationDate")LocalDate localDate);

    @Query("select avg(r.averageRating) from Rating r where r.insertDate=:valuationDate")
    public Double getAvgRatingByDate(@Param("valuationDate") LocalDate localDate);


    @Query("select avg(r.averageRating) from Rating r")
    public Double getOverallAvgRating();

    @Query("select count(mov.id) from Movie mov")
    public Long getValuationMovies();

    @Query("select count(mov.id) from Movie mov where mov.insertDate=:valuationDate")
    public Long getNewMovies(@Param("valuationDate") LocalDate localDate);

    @Query("select count(mov.id) from Movie mov where mov.insertDate=:valuationDate and mov.isAdult=true")
    public Long getNumAdultMoviesByDate(@Param("valuationDate") LocalDate valuationDate);

    @Query("select count(mov.id) from Movie mov where mov.isAdult=true")
    public Long getTotalNumAdultMovies();

    @Query("select avg(mov.runtimeMinutes) from Movie mov where mov.insertDate=:valuationDate and mov.runtimeMinutes is not null")
    public Double getAvgRuntimeMinutesByDate(@Param("valuationDate") LocalDate valuationDate);

    @Query("select avg(mov.runtimeMinutes) from Movie mov where mov.runtimeMinutes is not null")
    public Double getAvgOverallRuntimeMinutes();

    @Modifying
    @Query("delete from DailySummary d where d.valuationDate=:date")
    public void deleteByEndRatingDate(@Param("date") LocalDate date);

    @Modifying
    @Query(value = "truncate daily_summary",nativeQuery = true)
    public void truncate();


}
