package com.luca.imdb.movie.reports.service.impl;

import com.luca.imdb.movie.reports.consumer.MovieConsumer;
import com.luca.imdb.movie.reports.exception.LoadingException;
import com.luca.imdb.movie.reports.producer.MovieProducer;
import com.luca.imdb.movie.reports.consumer.RatingConsumer;
import com.luca.imdb.movie.reports.producer.RatingProducer;
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
import com.luca.imdb.movie.reports.util.RunnableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.zip.GZIPInputStream;

@Service
public class TsvLoaderServiceImpl implements TsvLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(TsvLoaderServiceImpl.class);

    private final MovieService movieService;

    private final RatingService ratingService;

    private final ImdbProperties imdbProperties;

    private final GenreService genreService;

    private final ExecutionProperties loadingProperties;

    private final RunnableFactory runnableFactory;


    public TsvLoaderServiceImpl(MovieService movieService, RatingService ratingService, ImdbProperties imdbProperties, GenreService genreService, ExecutionProperties loadingProperties,RunnableFactory runnableFactory) {
        this.movieService = movieService;
        this.ratingService = ratingService;
        this.imdbProperties = imdbProperties;
        this.genreService = genreService;
        this.loadingProperties = loadingProperties;
        this.runnableFactory=runnableFactory;

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
                   logger.debug("Skipped movie with code: {}",movieRow.gettConst());

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
        ExecutorService executor = Executors.newFixedThreadPool(2);

        MovieConsumer consumer=runnableFactory.createMovieConsumer();
        MovieProducer producer=runnableFactory.createMovieProducer();

        BlockingQueue<List<MovieRow>> queue=new LinkedBlockingQueue<>();
        producer.setMovieQueue(queue);
        consumer.setMovieQueue(queue);

        executor.submit(producer);
        executor.submit(consumer);

        executor.shutdown();
        try {
            executor.awaitTermination(loadingProperties.getTimeoutSeconds(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Lettura di titles tsv interrotta",e);
            throw new LoadingException("Lettura di titles tsv interrotta",e);
        }
    }

    @Override
    public void loadRatings() {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        RatingConsumer consumer=runnableFactory.createRatingConsumer();
        RatingProducer producer=runnableFactory.createRatingProducer();

        BlockingQueue<List<RatingRow>> queue=new LinkedBlockingQueue<>();

        producer.setRatingQueue(queue);
        consumer.setRatingQueue(queue);

        executor.submit(producer);
        executor.submit(consumer);

        executor.shutdown();
        try {
            executor.awaitTermination(loadingProperties.getTimeoutSeconds(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Lettura di ratings tsv interrotta",e);
            throw new LoadingException("Lettura di ratings tsv interrotta",e);
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


}
