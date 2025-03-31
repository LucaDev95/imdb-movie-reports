package com.luca.imdb.movie.reports.repository;

import com.luca.imdb.movie.reports.entitiy.TrendingMoviesSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TrendingMoviesSummaryRepository extends JpaRepository<TrendingMoviesSummary,Long>, TrendingMoviesSummaryRepositoryExtension {


    @Modifying
    @Query(value = "delete from trending_movies_summary ts using movie mov where ts.movie_id=mov.id and mov.insert_date<:limitDate and mov.year<:limitYear",nativeQuery = true)
    public int deleteOldTrendingMovies(@Param("limitDate") LocalDate limitDate, @Param("limitYear") Integer limitYear);

    @Modifying
    @Query("delete from TrendingMoviesSummary d where d.endRating.insertDate=:date")
    public void deleteByEndRatingDate(@Param("date") LocalDate date);


    @Modifying
    @Query(value = "truncate trending_movies_summary",nativeQuery = true)
    public void truncate();



}
