package com.luca.imdb_movie_rating.repository;

import com.luca.imdb_movie_rating.dto.GenreSummaryDoubleResult;
import com.luca.imdb_movie_rating.dto.GenreSummaryLongResult;
import com.luca.imdb_movie_rating.entitiy.DailyGenreSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DailyGenreSummaryRepository extends JpaRepository<DailyGenreSummary,Long> {

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(sum(r.numVotes),g.genre) from Rating r join r.movie m join m.genreList g where g.genreId=:genreId and r.insertDate=:valuationDate group by g.genre")
    public List<GenreSummaryLongResult> getSumNumVotesUntilDate(@Param("genreId") Integer genreId, @Param("valuationDate")LocalDate localDate);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryDoubleResult(avg(r.averageRating),g.genre) from Rating r join r.movie m join m.genreList g where g.genreId=:genreId and r.insertDate=:valuationDate group by g.genre")
    public List<GenreSummaryDoubleResult> getAvgRatingByDate(@Param("genreId") Integer genreId, @Param("valuationDate") LocalDate localDate);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryDoubleResult(avg(r.averageRating),g.genre) from Rating r join r.movie m join m.genreList g where g.genreId=:genreId group by g.genre")
    public List<GenreSummaryDoubleResult> getOverallAvgRating(@Param("genreId") Integer genreId);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(count(m.id),g.genre) from Movie m join m.genreList g where g.genreId=:genreId group by g.genre")
    public List<GenreSummaryLongResult> getValuationMovies(@Param("genreId") Integer genreId);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(count(m.id),g.genre) from Movie m join m.genreList g where g.genreId=:genreId and m.insertDate=:valuationDate group by g.genre")
    public List<GenreSummaryLongResult> getNewMovies(@Param("genreId") Integer genreId,@Param("valuationDate") LocalDate localDate);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(count(m.id),g.genre) from Movie m join m.genreList g where g.genreId=:genreId and m.isAdult=true group by g.genre")
    public List<GenreSummaryLongResult> getTotalNumAdultMovies(@Param("genreId") Integer genreId);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(avg(m.runtimeMinutes),g.genre) from Movie m join m.genreList g where g.genreId=:genreId and m.insertDate=:valuationDate and m.runtimeMinutes is not null group by g.genre")
    public  List<GenreSummaryDoubleResult> getAvgRuntimeMinutesByDate(@Param("genreId") Integer genreId,@Param("valuationDate") LocalDate valuationDate);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(avg(m.runtimeMinutes),g.genre) from Movie m join m.genreList g where g.genreId=:genreId and m.runtimeMinutes is not null group by g.genre")
    public  List<GenreSummaryDoubleResult> getAvgOverallRuntimeMinutes(@Param("genreId") Integer genreId);

    @Modifying
    @Query("delete from DailyGenreSummary d where d.valuationDate=:date")
    public void deleteByEndRatingDate(@Param("date") LocalDate date);

    @Modifying
    @Query(value = "truncate daily_genre_summary",nativeQuery = true)
    public void truncate();

}
