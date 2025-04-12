package com.luca.imdb.movie.reports.producer;

import com.luca.imdb.movie.reports.config.ExecutionProperties;
import com.luca.imdb.movie.reports.config.ImdbProperties;
import com.luca.imdb.movie.reports.dto.MovieRow;
import com.luca.imdb.movie.reports.entitiy.Movie;
import com.luca.imdb.movie.reports.enums.Genre;
import com.luca.imdb.movie.reports.exception.LoadingException;
import com.luca.imdb.movie.reports.service.GenreService;
import com.luca.imdb.movie.reports.service.MovieService;
import com.luca.imdb.movie.reports.service.impl.TsvLoaderServiceImpl;
import com.luca.imdb.movie.reports.util.ImdbConstants;
import com.luca.imdb.movie.reports.util.ParsingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.zip.GZIPInputStream;

@Component
@Scope("prototype")
public class MovieProducer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MovieProducer.class);

    private final MovieService movieService;
    private final ImdbProperties imdbProperties;
    private final ExecutionProperties executionProperties;
    private  BlockingQueue<List<MovieRow>> movieQueue;



    public MovieProducer(MovieService movieService, ImdbProperties imdbProperties, ExecutionProperties executionProperties){
        this.movieService=movieService;
        this.imdbProperties=imdbProperties;
        this.executionProperties=executionProperties;
    }


    @Override
    public void run() {

        logger.info("Movie producer Started");
        List<MovieRow> movieRowList=new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        Map<String, Long> tConstMap = movieService.getMovieNextTConstMap(null);
        String maxString="";
        if (!tConstMap.isEmpty()) {
            maxString = Collections.max(tConstMap.keySet(), Comparator.naturalOrder());

        }

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

                                if(movieRowList.size() >= executionProperties.getMovieInsertSize()){
                                    movieQueue.put(movieRowList);
                                    movieRowList=new ArrayList<>();
                                }
                            }
                        }
                    }

                }catch(RuntimeException e){
                    logger.debug("Skipped movie row with code: {}",movieRow.gettConst());

                }
            }


            movieQueue.put(movieRowList);
            logger.info("End reading movie tsv");
            movieQueue.put(new ArrayList<>());

        }catch(IOException | InterruptedException e){
            logger.error("Error while reading titles tsv file from imdb",e);
            throw new LoadingException("Error while reading titles tsv file from imdb",e);
        }



    }

    public void setMovieQueue(BlockingQueue<List<MovieRow>> movieQueue) {
        this.movieQueue = movieQueue;
    }

}
