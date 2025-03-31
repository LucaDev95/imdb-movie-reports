package com.luca.imdb.movie.reports.service.impl;

import com.luca.imdb.movie.reports.dto.MovieIdAndTConst;
import com.luca.imdb.movie.reports.dto.RatingRow;
import com.luca.imdb.movie.reports.entitiy.Movie;
import com.luca.imdb.movie.reports.entitiy.Rating;
import com.luca.imdb.movie.reports.dto.MovieRow;
import com.luca.imdb.movie.reports.entitiy.GenreEntity;
import com.luca.imdb.movie.reports.enums.Genre;
import com.luca.imdb.movie.reports.repository.GenreRepository;
import com.luca.imdb.movie.reports.repository.MovieRepository;
import com.luca.imdb.movie.reports.repository.RatingRepository;
import com.luca.imdb.movie.reports.service.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    private final RatingRepository ratingRepository;

    private final GenreRepository genreRepository;

    public MovieServiceImpl(MovieRepository movieRepository,RatingRepository ratingRepository,GenreRepository genreRepository){
        this.movieRepository=movieRepository;
        this.ratingRepository=ratingRepository;
        this.genreRepository=genreRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public long countMovies() {
       return this.movieRepository.count();
    }

    @Override
    @Transactional
    public void saveNewMovies(List<MovieRow> movieRowList,Map<Genre,Integer> genreMap) {

        List<Movie> newMovies=movieRowList.stream().map(m->{
            Movie movie=new Movie();
            movie.setYear(m.getYear());
            movie.setRuntimeMinutes(m.getRuntimeMinutes());
            movie.setOriginalTitle(m.getOriginalTitle());

            movie.settConst(m.gettConst());
            movie.setAdult(m.isAdult());
            movie.setPrimaryTitle(m.getPrimaryTitle());


            List<GenreEntity> genreList=new ArrayList<>();
            for(Genre genre : m.getGenres()){
                Integer genreId=genreMap.get(genre);
                GenreEntity genreEntity=genreRepository.getReferenceById(genreId);
                genreList.add(genreEntity);

            }
            movie.setGenreList(genreList);

            RatingRow ratingRow=m.getRatingRow();
            Rating rating=new Rating();

            if(ratingRow!=null){
                rating.setNumVotes(ratingRow.numVotes());

                rating.setAverageRating(ratingRow.averageRating());
                rating.setMovie(movie);
                List<Rating> ratingList=new ArrayList<>();
                ratingList.add(rating);

                movie.setRatingList(ratingList);
            }


            return movie;

        }).toList();


        movieRepository.saveAll(newMovies);

    }


    @Override
    @Transactional
    public void deleteCurrentDate() {

        LocalDate currentDate=LocalDate.now();

        movieRepository.deleteMovieGenreByInsertDate(currentDate);
        movieRepository.deleteByInsertDate(currentDate);
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String,Long> getMovieNextTConstMap(String tConst){

        return movieRepository.findMovieIdAndTConst(tConst).stream().collect(Collectors.toMap(MovieIdAndTConst::tConst,MovieIdAndTConst::id));
    }



}
