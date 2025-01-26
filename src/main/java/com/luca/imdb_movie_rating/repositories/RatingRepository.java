package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating,Long> , RatingRepositoryExtension{



}
