package com.luca.imdb.movie.reports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@ConfigurationPropertiesScan("com.luca.imdb.movie.reports.config")
public class ImdbMovieReportsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImdbMovieReportsApplication.class, args);
	}


}
