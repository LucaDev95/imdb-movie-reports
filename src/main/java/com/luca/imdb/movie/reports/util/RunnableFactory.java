package com.luca.imdb.movie.reports.util;

import com.luca.imdb.movie.reports.consumer.MovieConsumer;
import com.luca.imdb.movie.reports.producer.MovieProducer;
import com.luca.imdb.movie.reports.consumer.RatingConsumer;
import com.luca.imdb.movie.reports.producer.RatingProducer;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public abstract class RunnableFactory {

    @Lookup
    public abstract MovieConsumer createMovieConsumer();

    @Lookup
    public abstract MovieProducer createMovieProducer();

    @Lookup
    public abstract RatingProducer createRatingProducer();

    @Lookup
    public abstract RatingConsumer createRatingConsumer();


}
