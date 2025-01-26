package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.dtos.RatingResult;
import com.luca.imdb_movie_rating.entities.Rating;
import com.luca.imdb_movie_rating.repositories.RatingRepository;
import com.luca.imdb_movie_rating.services.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository){
        this.ratingRepository=ratingRepository;
    }


    @Override
    @Transactional
    public void loadRatings(List<Rating> ratingList){
        this.ratingRepository.saveAll(ratingList);

    }


    @Override
    @Transactional(readOnly = true)
    public List<RatingResult> loadRatingResult(){
       List<RatingResult> ratingResult= ratingRepository.findRatingResultList();

       System.out.println("rating results");

        ratingResult.forEach(System.out::println);

        return ratingResult;

    }
}
