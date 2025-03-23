package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.entities.GenreEntity;
import com.luca.imdb_movie_rating.enums.Genre;
import com.luca.imdb_movie_rating.repositories.GenreRepository;
import com.luca.imdb_movie_rating.services.GenreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository){
        this.genreRepository=genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Genre, Integer> getGenreMap() {

       return genreRepository.findAll().stream().collect(Collectors.toMap(GenreEntity::getGenre,GenreEntity::getGenreId,(o1,o2)->o1,()->new EnumMap<>(Genre.class)));
    }
}
