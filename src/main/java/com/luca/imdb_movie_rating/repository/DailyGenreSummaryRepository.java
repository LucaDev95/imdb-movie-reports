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

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(sum(r.numVotes),g.genre) from Rating r join r.movie m join m.genreList g where r.insertDate=:valuationDate group by g.genre")
    public List<GenreSummaryLongResult> getSumNumVotesUntilDate(@Param("valuationDate")LocalDate localDate);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryDoubleResult(avg(r.averageRating),g.genre) from Rating r join r.movie m join m.genreList g where r.insertDate=:valuationDate group by g.genre")
    public List<GenreSummaryDoubleResult> getAvgRatingByDate(@Param("valuationDate") LocalDate localDate);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryDoubleResult(avg(r.averageRating),g.genre) from Rating r join r.movie m join m.genreList g group by g.genre")
    public List<GenreSummaryDoubleResult> getTotalAvgRating();

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(count(m.id),g.genre) from Movie m join m.genreList g group by g.genre")
    public List<GenreSummaryLongResult> getMoviesAnalyzed();

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(count(m.id),g.genre) from Movie m join m.genreList g where m.insertDate>=:startDate group by g.genre")
    public List<GenreSummaryLongResult> getNewMovies(@Param("startDate") LocalDate startDate);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(count(m.id),g.genre) from Movie m join m.genreList g where m.isAdult=true group by g.genre")
    public List<GenreSummaryLongResult> getTotalNumAdultMovies();

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(avg(m.runtimeMinutes),g.genre) from Movie m join m.genreList g where m.insertDate>=:startDate and m.runtimeMinutes is not null group by g.genre")
    public  List<GenreSummaryDoubleResult> getAvgRuntimeMinutesByDate(@Param("startDate") LocalDate startDate);

    @Query("select new com.luca.imdb_movie_rating.dto.GenreSummaryLongResult(avg(m.runtimeMinutes),g.genre) from Movie m join m.genreList g where m.runtimeMinutes is not null group by g.genre")
    public  List<GenreSummaryDoubleResult> getAvgTotalRuntimeMinutes();

    @Modifying
    @Query("delete from DailyGenreSummary d where d.valuationDate=:date")
    public void deleteByEndRatingDate(@Param("date") LocalDate date);

    @Modifying
    @Query(value = "truncate daily_genre_summary",nativeQuery = true)
    public void truncate();

}
