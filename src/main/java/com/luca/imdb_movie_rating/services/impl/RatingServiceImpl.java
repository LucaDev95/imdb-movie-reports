package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.dtos.TrendingMovieDto;
import com.luca.imdb_movie_rating.dtos.RatingRow;
import com.luca.imdb_movie_rating.entities.Movie;
import com.luca.imdb_movie_rating.entities.Rating;
import com.luca.imdb_movie_rating.repositories.MovieRepository;
import com.luca.imdb_movie_rating.repositories.RatingRepository;
import com.luca.imdb_movie_rating.services.RatingService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final MovieRepository movieRepository;

    public RatingServiceImpl(RatingRepository ratingRepository,MovieRepository movieRepository,EntityManager em){
        this.ratingRepository=ratingRepository;
        this.movieRepository=movieRepository;

    }

    @Override
    @Transactional
    public void saveRatingRows(List<RatingRow> ratingRows) {
        List<Rating> ratingList=ratingRows.stream().map(r->{
            Rating rating=new Rating();

            rating.setNumVotes(r.numVotes());
            rating.setAverageRating(r.averageRating());

            Movie movie=movieRepository.getReferenceById(r.movieId());
            rating.setMovie(movie);

            return rating;
        }).toList();

        ratingRepository.saveAll(ratingList);
    }

    @Override
    @Transactional
    public void deleteCurrentRatings() {

        LocalDate today=LocalDate.now();
        ratingRepository.deleteCurrentRatings(today);
    }

}
