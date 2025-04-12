package com.luca.imdb.movie.reports.producer;

import com.luca.imdb.movie.reports.config.ExecutionProperties;
import com.luca.imdb.movie.reports.config.ImdbProperties;
import com.luca.imdb.movie.reports.dto.MovieRow;
import com.luca.imdb.movie.reports.dto.RatingRow;
import com.luca.imdb.movie.reports.exception.LoadingException;
import com.luca.imdb.movie.reports.service.MovieService;
import com.luca.imdb.movie.reports.service.RatingService;
import com.luca.imdb.movie.reports.util.ParsingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.zip.GZIPInputStream;

@Component
@Scope("prototype")
public class RatingProducer implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RatingProducer.class);

    private final MovieService movieService;

    private final RatingService ratingService;

    private final ImdbProperties imdbProperties;
    private final ExecutionProperties executionProperties;

    private BlockingQueue<List<RatingRow>> ratingQueue;

    public RatingProducer(MovieService movieService, RatingService ratingService,ImdbProperties imdbProperties,ExecutionProperties executionProperties){
        this.movieService=movieService;
        this.ratingService=ratingService;
        this.imdbProperties=imdbProperties;
        this.executionProperties=executionProperties;
    }

    @Override
    public void run() {
        List<RatingRow> ratingRowList = new ArrayList<>();

        Map<String, Long> tConstMap = movieService.getMovieNextTConstMap(null);
        logger.info("Rating producer Started");
        logger.info("tConstMap size : {}", tConstMap.size());

        String maxString = "";

        if (!tConstMap.isEmpty()) {
            maxString = Collections.max(tConstMap.keySet(), Comparator.naturalOrder());
        }

        try (BufferedReader ratingsBufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(URI.create(imdbProperties.getRatingsGz()).toURL().openStream())))) {

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

                        logger.debug("found movie for tConst {}", tConst);

                        RatingRow rating = new RatingRow(movieId, ParsingUtils.parseAvgrating(arr), ParsingUtils.parseNumVotes(arr));


                        ratingRowList.add(rating);
                        logger.info("Read rating row : {}",rating);
                        if (ratingRowList.size() >= executionProperties.getRatingInsertSize()) {
                            ratingQueue.put(ratingRowList);
                            ratingRowList=new ArrayList<>();
                        }
                    }


                }catch(RuntimeException e){
                    logger.debug("Skipped rating row with code: {}",tConst);
                }
            }

            ratingQueue.put(ratingRowList);
            logger.info("End reading rating tsv");
            ratingQueue.put(new ArrayList<>());

        }catch(IOException | InterruptedException e){
            logger.error("Error while reading ratings tsv file from imdb",e);
            throw new LoadingException("Error while reading ratings tsv file from imdb",e);
        }
    }

    public void setRatingQueue(BlockingQueue<List<RatingRow>> ratingQueue) {
        this.ratingQueue = ratingQueue;
    }
}
