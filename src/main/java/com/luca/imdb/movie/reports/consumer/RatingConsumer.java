package com.luca.imdb.movie.reports.consumer;

import com.luca.imdb.movie.reports.dto.RatingRow;
import com.luca.imdb.movie.reports.exception.LoadingException;
import com.luca.imdb.movie.reports.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@Component
@Scope("prototype")
public class RatingConsumer implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RatingConsumer.class);

    private final RatingService ratingService;

    private BlockingQueue<List<RatingRow>> ratingQueue;

    public RatingConsumer(RatingService ratingService){
        this.ratingService=ratingService;
    }

    @Override
    public void run() {
        logger.info("Rating consumer started");

        try {
            List<RatingRow> ratingRowList= ratingQueue.take();

            while(!ratingRowList.isEmpty()){
                logger.info("Saving next rating rows list");
                ratingService.saveRatingRows(ratingRowList);
                ratingRowList= ratingQueue.take();
            }

            logger.info("End consuming rating rows");

        } catch (InterruptedException e) {
            logger.error("Error while reading ratings tsv file from imdb",e);
            throw new LoadingException("Error while reading ratings tsv file from imdb",e);

        }
    }

    public void setRatingQueue(BlockingQueue<List<RatingRow>> ratingQueue) {
        this.ratingQueue = ratingQueue;
    }
}
