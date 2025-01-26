package com.luca.imdb_movie_rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories
@EnableTransactionManagement
public class ImdbMovieRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImdbMovieRatingApplication.class, args);
	}

}
