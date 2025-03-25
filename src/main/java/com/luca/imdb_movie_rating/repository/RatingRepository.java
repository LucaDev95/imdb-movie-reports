package com.luca.imdb_movie_rating.repository;

import com.luca.imdb_movie_rating.entitiy.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface RatingRepository extends JpaRepository<Rating,Long>{


    @Modifying
    @Query("delete from Rating r where r.insertDate >=:localDate")
    public void deleteCurrentRatings(@Param("localDate") LocalDate localDate);

    @Modifying
    @Query(value = "truncate Rating CASCADE",nativeQuery = true)
    public void truncate();



}
