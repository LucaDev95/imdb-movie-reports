package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.entities.DailyTrendingMoviesSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface DailyTrendingMoviesSummaryRepository extends JpaRepository<DailyTrendingMoviesSummary,Long>,DailyTrendingMoviesSummaryRepositoryExtension {

    @Modifying
    @Query("delete from DailyTrendingMoviesSummary d where d.endRating.insertDate=:date")
    public void deleteByEndRatingDate(@Param("date") LocalDate date);


    @Modifying
    @Query(value = "truncate daily_trending_movies_summary",nativeQuery = true)
    public void truncate();

}
