package com.luca.imdb.movie.reports.repository;

import com.luca.imdb.movie.reports.entitiy.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface RatingRepository extends JpaRepository<Rating,Long>{


    @Query("select count(r) from Rating r where r.insertDate<:reportIntervalDate")
    public Long countRatingsInReportInterval(@Param("reportIntervalDate") LocalDate reportIntervalDate);

    @Modifying
    @Query(value = "delete from rating r using movie mov where r.movie_id=mov.id and mov.insert_date<:limitDate and mov.year<:limitYear",nativeQuery = true)
    public int deleteOldRatings(@Param("limitDate") LocalDate limitDate, @Param("limitYear") Integer limitYear);

    @Modifying
    @Query("delete from Rating r where r.insertDate >=:localDate")
    public void deleteCurrentRatings(@Param("localDate") LocalDate localDate);

    @Modifying
    @Query(value = "truncate Rating CASCADE",nativeQuery = true)
    public void truncate();



}
