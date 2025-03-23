package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.entities.DailyGenreSummary;
import com.luca.imdb_movie_rating.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface DailyGenreSummaryRepository extends JpaRepository<DailyGenreSummary,Long> {

    @Query("select sum(r.numVotes) from Rating r join r.movie m join m.genreList g where g.genreId=:genreId and r.insertDate=:valuationDate")
    public Long getSumNumVotesUntilDate(@Param("genreId") Integer genreId,@Param("valuationDate")LocalDate localDate);

    @Query("select avg(r.averageRating) from Rating r join r.movie m join m.genreList g where g.genreId=:genreId and r.insertDate=:valuationDate")
    public Double getAvgRatingByDate(@Param("genreId") Integer genreId,@Param("valuationDate") LocalDate localDate);

    @Query("select avg(r.averageRating) from Rating r join r.movie m join m.genreList g where g.genreId=:genreId")
    public Double getOverallAvgRating(@Param("genreId") Integer genreId);

    @Query("select count(m.id) from Movie m join m.genreList g where g.genreId=:genreId")
    public Long getValuationMovies(@Param("genreId") Integer genreId);

    @Query("select count(m.id) from Movie m join m.genreList g where g.genreId=:genreId and m.insertDate=:valuationDate")
    public Long getNewMovies(@Param("genreId") Integer genreId,@Param("valuationDate") LocalDate localDate);

    @Query("select count(m.id) from Movie m join m.genreList g where g.genreId=:genreId and m.insertDate=:valuationDate and m.isAdult=true")
    public Long getNumAdultMoviesByDate(@Param("genreId") Integer genreId,@Param("valuationDate") LocalDate valuationDate);

    @Query("select count(m.id) from Movie m join m.genreList g where g.genreId=:genreId and m.isAdult=true")
    public Long getTotalNumAdultMovies(@Param("genreId") Integer genreId);

    @Query("select avg(m.runtimeMinutes) from Movie m join m.genreList g where g.genreId=:genreId and m.insertDate=:valuationDate and m.runtimeMinutes is not null")
    public Double getAvgRuntimeMinutesByDate(@Param("genreId") Integer genreId,@Param("valuationDate") LocalDate valuationDate);

    @Query("select avg(m.runtimeMinutes) from Movie m join m.genreList g where g.genreId=:genreId and m.runtimeMinutes is not null")
    public Double getAvgOverallRuntimeMinutes(@Param("genreId") Integer genreId);

    @Modifying
    @Query("delete from DailyGenreSummary d where d.valuationDate=:date")
    public void deleteByEndRatingDate(@Param("date") LocalDate date);

    @Modifying
    @Query(value = "truncate daily_genre_summary",nativeQuery = true)
    public void truncate();

}
