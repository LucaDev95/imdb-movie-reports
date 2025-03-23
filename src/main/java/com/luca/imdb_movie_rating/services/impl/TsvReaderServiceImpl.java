package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.config.ImdbProperties;
import com.luca.imdb_movie_rating.config.LoadingProperties;
import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.dtos.RatingRow;
import com.luca.imdb_movie_rating.entities.Movie;
import com.luca.imdb_movie_rating.enums.Genre;
import com.luca.imdb_movie_rating.services.GenreService;
import com.luca.imdb_movie_rating.services.MovieService;
import com.luca.imdb_movie_rating.services.RatingService;
import com.luca.imdb_movie_rating.services.TsvReaderService;
import com.luca.imdb_movie_rating.utils.ImdbConstants;
import com.luca.imdb_movie_rating.utils.ParsingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.GZIPInputStream;

@Service
public class TsvReaderServiceImpl implements TsvReaderService {

    private static final Logger logger = LoggerFactory.getLogger(TsvReaderServiceImpl.class);

    private final MovieService movieService;

    private final RatingService ratingService;

    private final ImdbProperties imdbProperties;

    private final GenreService genreService;

    private final LoadingProperties loadingProperties;


    public TsvReaderServiceImpl(MovieService movieService, RatingService ratingService, ImdbProperties imdbProperties,GenreService genreService,LoadingProperties loadingProperties){
        this.movieService=movieService;
        this.ratingService=ratingService;
        this.imdbProperties=imdbProperties;
        this.genreService=genreService;
        this.loadingProperties=loadingProperties;

    }

    @Override
    public void loadFirstTime(){

        try( BufferedReader basicsBufferedReader =  new BufferedReader(new InputStreamReader(new GZIPInputStream(URI.create(imdbProperties.getBasicsGz()).toURL().openStream())));
             BufferedReader ratingsBufferedReader =  new BufferedReader(new InputStreamReader(new GZIPInputStream(URI.create(imdbProperties.getRatingsGz()).toURL().openStream())))){

            int startValuationYear=LocalDate.now().getYear()-loadingProperties.getMaxPreviousYears();

            List<MovieRow> movieRowList=new ArrayList<>();

           Map<Genre,Integer> genreMap= genreService.getGenreMap();

            basicsBufferedReader.readLine();
            ratingsBufferedReader.readLine();

            String[] ratingArr=ratingsBufferedReader.readLine().split("\t");

            while(basicsBufferedReader.ready()){

                MovieRow movieRow=new MovieRow();

                String[] arr=basicsBufferedReader.readLine().split("\t");

                try {


                if(ParsingUtil.parseTitleType(arr).equals(ImdbConstants.MOVIE)){

                    movieRow.settConst(ParsingUtil.parseTConst(arr));

                        movieRow.setYear(ParsingUtil.parseYear(arr));

                        if (movieRow.getYear() >= startValuationYear) {


                            movieRow=ParsingUtil.parseOtherFields(arr,movieRow);

                            RatingRow rating=null;

                            while(ratingArr[0].compareTo(movieRow.gettConst())<0){

                                 ratingArr=ratingsBufferedReader.readLine().split("\t");
                            }

                            if(ratingArr[0].compareTo(movieRow.gettConst())==0){

                                rating=new RatingRow(null,ParsingUtil.parseAvgrating(ratingArr),ParsingUtil.parseNumVotes(ratingArr));
                            }

                            movieRow.setRatingRow(rating);

                            movieRowList.add(movieRow);
                        }

                    }

                }catch(Exception e){
                    // errore relativo al parsing, loggare e basta


                    continue;
                }

                if(movieRowList.size()>=loadingProperties.getMovieInsertSize()){
                    saveAndClearList(movieRowList, genreMap);
                }


            }

            if(!movieRowList.isEmpty()){
                saveAndClearList(movieRowList, genreMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
            // erorre di I/O, inviare la mail
        }


    }

    @Override
    public void readTitlesTsv() {

        List<MovieRow> movieRowList=new ArrayList<>();

        int currentYear=LocalDate.now().getYear();

        Map<String,Long> tConstMap=movieService.getMovieNextTConstMap(null);

        String maxString="";

        if(tConstMap.size()>0){
            maxString=Collections.max(tConstMap.keySet(),Comparator.naturalOrder());
        }

        Map<Genre,Integer> genreMap= genreService.getGenreMap();

        try( BufferedReader basicsBufferedReader =  new BufferedReader(new InputStreamReader(new GZIPInputStream(URI.create(imdbProperties.getBasicsGz()).toURL().openStream())))){

            basicsBufferedReader.readLine();

            while(basicsBufferedReader.ready()){

                String[] arr=basicsBufferedReader.readLine().split("\t");

                MovieRow movieRow=new MovieRow();

                try{

                if(ParsingUtil.parseTitleType(arr).equals(ImdbConstants.MOVIE)){


                    movieRow.settConst(ParsingUtil.parseTConst(arr));

                    movieRow.setYear(ParsingUtil.parseYear(arr));

                        if(movieRow.getYear()!=null && movieRow.getYear()>=currentYear){
                            while(maxString.compareTo(movieRow.gettConst())<0 && tConstMap.size()>0){

                                tConstMap= movieService.getMovieNextTConstMap(maxString);
                                if(tConstMap.size()>0){
                                    maxString=Collections.max(tConstMap.keySet(),Comparator.naturalOrder());
                                }

                            }

                            if(!tConstMap.containsKey(movieRow.gettConst())){
                               movieRow=ParsingUtil.parseOtherFields(arr,movieRow);

                                movieRowList.add(movieRow);
                            }
                        }

                    }


                }catch(Exception e){
                    // erorre nel parsing, loggo e continuo
                    continue;
                }

                if(movieRowList.size()>=loadingProperties.getMovieInsertSize()){
                    saveAndClearList(movieRowList, genreMap);
                }
            }

            if(!movieRowList.isEmpty()) {
                saveAndClearList(movieRowList, genreMap);
            }



        }catch (IOException e) {
            // lanciare eccezione e inviare mail

            e.printStackTrace();
        }

    }

    @Override
    public void readRatingsTsv() {

        List<RatingRow> ratingRowList=new ArrayList<>();

        Map<String,Long> tConstMap= movieService.getMovieNextTConstMap(null);

        logger.info("tConstMap size : {}",tConstMap.size());

        String maxString="";

        if(tConstMap.size()>0){
            maxString=Collections.max(tConstMap.keySet(),Comparator.naturalOrder());
        }

        try(BufferedReader ratingsBufferedReader =  new BufferedReader(new InputStreamReader(new GZIPInputStream(URI.create(imdbProperties.getRatingsGz()).toURL().openStream())))){

            // skip headers
            ratingsBufferedReader.readLine();

            while(ratingsBufferedReader.ready()){

                try {

                    String[] arr = ratingsBufferedReader.readLine().split("\t");
                    String tConst = ParsingUtil.parseTConst(arr);

                    while (maxString.compareTo(tConst) < 0 && tConstMap.size() > 0) {
                        tConstMap = movieService.getMovieNextTConstMap(maxString);
                        if (tConstMap.size() > 0) {
                            maxString = Collections.max(tConstMap.keySet(), Comparator.naturalOrder());
                        }
                    }

                    Long movieId = tConstMap.get(tConst);


                    if (movieId != null) {

                        logger.info("found movie for tConst {}", tConst);

                        RatingRow rating = new RatingRow(movieId, ParsingUtil.parseAvgrating(arr), ParsingUtil.parseNumVotes(arr));


                        ratingRowList.add(rating);
                    }

                }catch(Exception e){
                    continue;
                    // errore parsing rating
                }

                if(ratingRowList.size()>=loadingProperties.getRatingInsertSize()){
                    saveAndClearList(ratingRowList);
                }

            }

            if(ratingRowList.size()>0){
                saveAndClearList(ratingRowList);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    private void saveAndClearList(List<MovieRow> movieRowList,Map<Genre,Integer> genreMap){
        try {


            movieService.saveNewMovies(movieRowList, genreMap);
            movieRowList.clear();

        }catch(RuntimeException e){
            // lanciare un eccezione relativa al salvataggio, sarà inviata via mail

        }
    }

    private void saveAndClearList(List<RatingRow> ratingRowList){
        try {


            ratingService.saveRatingRows(ratingRowList);
            ratingRowList.clear();

        }catch(RuntimeException e){
            // lanciare l'eccezione relativa al salvataggio
        }
    }



}
