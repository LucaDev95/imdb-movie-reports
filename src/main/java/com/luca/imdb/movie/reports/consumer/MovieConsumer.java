package com.luca.imdb.movie.reports.consumer;

import com.luca.imdb.movie.reports.dto.MovieRow;
import com.luca.imdb.movie.reports.enums.Genre;
import com.luca.imdb.movie.reports.exception.LoadingException;
import com.luca.imdb.movie.reports.service.GenreService;
import com.luca.imdb.movie.reports.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Component
@Scope("prototype")
public class MovieConsumer implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(MovieConsumer.class);

    private final MovieService movieService;

    private final GenreService genreService;

    private BlockingQueue<List<MovieRow>> movieQueue;


    public MovieConsumer(MovieService movieService, GenreService genreService){
        this.movieService=movieService;
        this.genreService=genreService;
    }

    @Override
    public void run() {
        Map<Genre, Integer> genreMap = genreService.getGenreMap();

        logger.info("Movie consumer started");

        try {
            List<MovieRow> movieRowList= movieQueue.take();

            while(!movieRowList.isEmpty()){
                logger.info("Saving next movie rows list");
                movieService.saveNewMovies(movieRowList, genreMap);
                movieRowList= movieQueue.take();
            }

            logger.info("End consuming movie rows");

        } catch (InterruptedException e) {
            logger.error("Error while reading titles tsv file from imdb",e);
            throw new LoadingException("Error while reading titles tsv file from imdb",e);

        }

    }

    public void setMovieQueue(BlockingQueue<List<MovieRow>> movieQueue) {
        this.movieQueue = movieQueue;



    }
}
