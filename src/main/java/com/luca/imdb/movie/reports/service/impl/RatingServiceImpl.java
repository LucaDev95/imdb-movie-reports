package com.luca.imdb.movie.reports.service.impl;

import com.luca.imdb.movie.reports.config.ExecutionProperties;
import com.luca.imdb.movie.reports.dto.RatingRow;
import com.luca.imdb.movie.reports.entitiy.Movie;
import com.luca.imdb.movie.reports.entitiy.Rating;
import com.luca.imdb.movie.reports.repository.MovieRepository;
import com.luca.imdb.movie.reports.repository.RatingRepository;
import com.luca.imdb.movie.reports.service.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final MovieRepository movieRepository;

    private final ExecutionProperties executionProperties;

    public RatingServiceImpl(RatingRepository ratingRepository,MovieRepository movieRepository,ExecutionProperties executionProperties){
        this.ratingRepository=ratingRepository;
        this.movieRepository=movieRepository;
        this.executionProperties=executionProperties;

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

    @Override
    public boolean checkReportGeneration() {
        LocalDate currentDate=executionProperties.getCurrentDate();

        LocalDate reportIntervalDate=currentDate.minusDays(executionProperties.getSummaryIntervalDays());

        return ratingRepository.countRatingsInReportInterval(reportIntervalDate)>0;
    }

}
