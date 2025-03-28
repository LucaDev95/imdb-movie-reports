package com.luca.imdb.movie.reports.service.impl;

import com.luca.imdb.movie.reports.exception.LoadingException;
import com.luca.imdb.movie.reports.service.TsvLoaderService;
import com.luca.imdb.movie.reports.util.ParsingUtils;
import com.luca.imdb.movie.reports.config.ImdbProperties;
import com.luca.imdb.movie.reports.config.ExecutionProperties;
import com.luca.imdb.movie.reports.dto.MovieRow;
import com.luca.imdb.movie.reports.dto.RatingRow;
import com.luca.imdb.movie.reports.enums.Genre;
import com.luca.imdb.movie.reports.service.GenreService;
import com.luca.imdb.movie.reports.service.MovieService;
import com.luca.imdb.movie.reports.service.RatingService;
import com.luca.imdb.movie.reports.util.ImdbConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.GZIPInputStream;

@Service
public class TsvLoaderServiceImpl implements TsvLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(TsvLoaderServiceImpl.class);

    private final MovieService movieService;

    private final RatingService ratingService;

    private final ImdbProperties imdbProperties;

    private final GenreService genreService;

    private final ExecutionProperties loadingProperties;


    public TsvLoaderServiceImpl(MovieService movieService, RatingService ratingService, ImdbProperties imdbProperties, GenreService genreService, ExecutionProperties loadingProperties) {
        this.movieService = movieService;
        this.ratingService = ratingService;
        this.imdbProperties = imdbProperties;
        this.genreService = genreService;
        this.loadingProperties = loadingProperties;

    }

    @Override
    public void reload() {

        try (BufferedReader basicsBufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(URI.create(imdbProperties.getBasicsGz()).toURL().openStream())));
             BufferedReader ratingsBufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(URI.create(imdbProperties.getRatingsGz()).toURL().openStream())))) {

            int startValuationYear = LocalDate.now().getYear() - loadingProperties.getMaxPreviousYears();

            List<MovieRow> movieRowList = new ArrayList<>();

            Map<Genre, Integer> genreMap = genreService.getGenreMap();

            basicsBufferedReader.readLine();
            ratingsBufferedReader.readLine();

            String[] ratingArr = ratingsBufferedReader.readLine().split("\t");

            while (basicsBufferedReader.ready()) {

                MovieRow movieRow = new MovieRow();

                String[] arr = basicsBufferedReader.readLine().split("\t");

                try {

                    if (ParsingUtils.parseTitleType(arr).equals(ImdbConstants.MOVIE)) {

                        movieRow.settConst(ParsingUtils.parseTConst(arr));

                        movieRow.setYear(ParsingUtils.parseYear(arr));

                        if (movieRow.getYear() >= startValuationYear) {


                            ParsingUtils.parseMovieFields(arr, movieRow);

                            RatingRow rating = null;

                            while (ratingArr[0].compareTo(movieRow.gettConst()) < 0) {

                                ratingArr = ratingsBufferedReader.readLine().split("\t");
                            }

                            if (ratingArr[0].compareTo(movieRow.gettConst()) == 0) {

                                rating = new RatingRow(null, ParsingUtils.parseAvgrating(ratingArr), ParsingUtils.parseNumVotes(ratingArr));
                            }

                            movieRow.setRatingRow(rating);

                            movieRowList.add(movieRow);

                            logger.info("Read movie row : {}",movieRow);
                            if(rating!=null){
                                logger.info("With rating row : {}",rating);
                            }

                        }

                    }

                } catch (Exception e) {
                   logger.info("Skipped movie with code: {}",movieRow.gettConst());

                    continue;
                }

                if (movieRowList.size() >= loadingProperties.getMovieInsertSize()) {
                    saveAndClearList(movieRowList, genreMap);
                }


            }

            if (!movieRowList.isEmpty()) {
                saveAndClearList(movieRowList, genreMap);
            }

        } catch (IOException e) {

            logger.error("Error while reading tsv files from imdb",e);
            throw new LoadingException("Error while reading tsv files from imdb",e);
        }


    }

    @Override
    public void loadTitles() {

        List<MovieRow> movieRowList = new ArrayList<>();

        int currentYear = LocalDate.now().getYear();

        Map<String, Long> tConstMap = movieService.getMovieNextTConstMap(null);

        String maxString = "";

        if (!tConstMap.isEmpty()) {
            maxString = Collections.max(tConstMap.keySet(), Comparator.naturalOrder());
        }

        Map<Genre, Integer> genreMap = genreService.getGenreMap();

        try (BufferedReader basicsBufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(URI.create(imdbProperties.getBasicsGz()).toURL().openStream())))) {

            basicsBufferedReader.readLine();

            while (basicsBufferedReader.ready()) {

                String[] arr = basicsBufferedReader.readLine().split("\t");

                MovieRow movieRow = new MovieRow();

                try {

                    if (ParsingUtils.parseTitleType(arr).equals(ImdbConstants.MOVIE)) {


                        movieRow.settConst(ParsingUtils.parseTConst(arr));

                        movieRow.setYear(ParsingUtils.parseYear(arr));

                        if (movieRow.getYear() != null && movieRow.getYear() >= currentYear) {
                            while (maxString.compareTo(movieRow.gettConst()) < 0 && !tConstMap.isEmpty()) {

                                tConstMap = movieService.getMovieNextTConstMap(maxString);
                                if (!tConstMap.isEmpty()) {
                                    maxString = Collections.max(tConstMap.keySet(), Comparator.naturalOrder());
                                }

                            }

                            if (!tConstMap.containsKey(movieRow.gettConst())) {
                                ParsingUtils.parseMovieFields(arr, movieRow);


                                movieRowList.add(movieRow);
                                logger.info("Read movie row : {}",movieRow);
                            }
                        }

                    }


                } catch (Exception e) {
                    logger.info("Skipped movie row with code: {}",movieRow.gettConst());
                    continue;
                }

                if (movieRowList.size() >= loadingProperties.getMovieInsertSize()) {
                    saveAndClearList(movieRowList, genreMap);
                }
            }

            if (!movieRowList.isEmpty()) {
                saveAndClearList(movieRowList, genreMap);
            }


        } catch (IOException e) {
            logger.error("Error while reading titles tsv file from imdb",e);
            throw new LoadingException("Error while reading titles tsv file from imdb",e);
        }

    }

    @Override
    public void loadRatings() {

        List<RatingRow> ratingRowList = new ArrayList<>();

        Map<String, Long> tConstMap = movieService.getMovieNextTConstMap(null);

        logger.info("tConstMap size : {}", tConstMap.size());

        String maxString = "";

        if (!tConstMap.isEmpty()) {
            maxString = Collections.max(tConstMap.keySet(), Comparator.naturalOrder());
        }

        try (BufferedReader ratingsBufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(URI.create(imdbProperties.getRatingsGz()).toURL().openStream())))) {

            // skip headers
            ratingsBufferedReader.readLine();

            String tConst=null;
            while (ratingsBufferedReader.ready()) {

                try {

                    String[] arr = ratingsBufferedReader.readLine().split("\t");
                    tConst = ParsingUtils.parseTConst(arr);

                    while (maxString.compareTo(tConst) < 0 && !tConstMap.isEmpty()) {
                        tConstMap = movieService.getMovieNextTConstMap(maxString);
                        if (!tConstMap.isEmpty()) {
                            maxString = Collections.max(tConstMap.keySet(), Comparator.naturalOrder());
                        }
                    }

                    Long movieId = tConstMap.get(tConst);


                    if (movieId != null) {

                        logger.info("found movie for tConst {}", tConst);

                        RatingRow rating = new RatingRow(movieId, ParsingUtils.parseAvgrating(arr), ParsingUtils.parseNumVotes(arr));


                        ratingRowList.add(rating);
                        logger.info("Read rating row : {}",rating);
                    }

                } catch (Exception e) {
                    logger.info("Skipped rating row with code: {}",tConst);
                    continue;

                }

                if (ratingRowList.size() >= loadingProperties.getRatingInsertSize()) {
                    saveAndClearList(ratingRowList);
                }

            }

            if (!ratingRowList.isEmpty()) {
                saveAndClearList(ratingRowList);
            }

        } catch (IOException e) {
            logger.error("Error while reading ratings tsv file from imdb",e);
            throw new LoadingException("Error while reading ratings tsv file from imdb",e);
        }


    }

    private void saveAndClearList(List<MovieRow> movieRowList, Map<Genre, Integer> genreMap) {
        try {


            movieService.saveNewMovies(movieRowList, genreMap);
            movieRowList.clear();

        } catch (RuntimeException e) {
            logger.error("Error while saving movies ",e);
            throw new LoadingException("Error while saving movies ",e);

        }
    }

    private void saveAndClearList(List<RatingRow> ratingRowList) {
        try {


            ratingService.saveRatingRows(ratingRowList);
            ratingRowList.clear();

        } catch (RuntimeException e) {
            logger.error("Error while saving ratings ",e);
            throw new LoadingException("Error while saving ratings ",e);
        }
    }


}
