package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.dtos.RatingRow;
import com.luca.imdb_movie_rating.entities.Movie;
import com.luca.imdb_movie_rating.entities.Rating;
import com.luca.imdb_movie_rating.repositories.MovieRepository;
import com.luca.imdb_movie_rating.repositories.RatingRepository;
import com.luca.imdb_movie_rating.services.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    private final RatingRepository ratingRepository;

    public MovieServiceImpl(MovieRepository movieRepository,RatingRepository ratingRepository){
        this.movieRepository=movieRepository;
        this.ratingRepository=ratingRepository;
    }


    @Override
    @Transactional
    public void saveNewMovies(List<MovieRow> movieRowList) {


        //List<Rating> ratings

        List<Movie> newMovies=movieRowList.stream().map(m->{
            Movie movie=new Movie();
            movie.setYear(m.year());
            movie.setRuntimeMinutes(m.runtimeMinutes());
            movie.setOriginalTitle(m.originalTitle());
            movie.setGenres(m.genres());
            movie.settConst(m.tConst());
            movie.setAdult(m.isAdult());
            movie.setPrimaryTitle(m.primaryTitle());

            RatingRow ratingRow=m.ratingRow();

            Rating r = new Rating();
            r.setMovie(movie);
            r.setAverageRating(ratingRow.averageRating());
            r.setNumVotes(ratingRow.numVotes());

            movie.addRating(r);

            return movie;

        }).toList();


        movieRepository.saveAll(newMovies);

    }


    @Override
    @Transactional(readOnly = true)
    public Map<String,Movie> findAllMoviesMap(){
        return movieRepository.findAll().stream().collect(Collectors.toMap(Movie::gettConst,m->m));

    }
}
