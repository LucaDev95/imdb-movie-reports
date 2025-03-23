package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long>{


    @Modifying
    @Query("delete from Rating r where r.insertDate >=:localDate")
    public void deleteCurrentRatings(@Param("localDate") LocalDate localDate);

    @Modifying
    @Query(value = "truncate Rating CASCADE",nativeQuery = true)
    public void truncate();



}
