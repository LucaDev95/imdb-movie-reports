package com.luca.imdb.movie.reports.repository;

import com.luca.imdb.movie.reports.dto.GenreSummaryDoubleResult;
import com.luca.imdb.movie.reports.dto.GenreSummaryLongResult;
import com.luca.imdb.movie.reports.entitiy.GenreSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface GenreSummaryRepository extends JpaRepository<GenreSummary,Long> {

    @Query("select new com.luca.imdb.movie.reports.dto.GenreSummaryLongResult(sum(r.numVotes),g.genreName) from Rating r join r.movie m join m.genreList g where r.insertDate=:valuationDate group by g.genreName")
    public List<GenreSummaryLongResult> getSumNumVotesUntilDate(@Param("valuationDate")LocalDate localDate);

    @Query("select new com.luca.imdb.movie.reports.dto.GenreSummaryDoubleResult(avg(r.averageRating),g.genreName) from Rating r join r.movie m join m.genreList g where r.insertDate=:valuationDate group by g.genreName")
    public List<GenreSummaryDoubleResult> getAvgRatingByDate(@Param("valuationDate") LocalDate localDate);

    @Query("select new com.luca.imdb.movie.reports.dto.GenreSummaryDoubleResult(avg(r.averageRating),g.genreName) from Rating r join r.movie m join m.genreList g group by g.genreName")
    public List<GenreSummaryDoubleResult> getTotalAvgRating();

    @Query("select new com.luca.imdb.movie.reports.dto.GenreSummaryLongResult(count(m.id),g.genreName) from Movie m join m.genreList g group by g.genreName")
    public List<GenreSummaryLongResult> getMoviesAnalyzed();

    @Query("select new com.luca.imdb.movie.reports.dto.GenreSummaryLongResult(count(m.id),g.genreName) from Movie m join m.genreList g where m.insertDate>=:startDate group by g.genreName")
    public List<GenreSummaryLongResult> getNewMovies(@Param("startDate") LocalDate startDate);

    @Query("select new com.luca.imdb.movie.reports.dto.GenreSummaryLongResult(count(m.id),g.genreName) from Movie m join m.genreList g where m.isAdult=true group by g.genreName")
    public List<GenreSummaryLongResult> getTotalNumAdultMovies();

    @Query("select new com.luca.imdb.movie.reports.dto.GenreSummaryDoubleResult(avg(m.runtimeMinutes),g.genreName) from Movie m join m.genreList g where m.insertDate>=:startDate and m.runtimeMinutes is not null group by g.genreName")
    public  List<GenreSummaryDoubleResult> getAvgRuntimeMinutesByDate(@Param("startDate") LocalDate startDate);

    @Query("select new com.luca.imdb.movie.reports.dto.GenreSummaryDoubleResult(avg(m.runtimeMinutes),g.genreName) from Movie m join m.genreList g where m.runtimeMinutes is not null group by g.genreName")
    public  List<GenreSummaryDoubleResult> getAvgTotalRuntimeMinutes();


    @Modifying
    @Query(value = "truncate genre_summary",nativeQuery = true)
    public void truncate();

}
