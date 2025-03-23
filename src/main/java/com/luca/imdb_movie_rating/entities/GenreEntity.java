package com.luca.imdb_movie_rating.entities;

import com.luca.imdb_movie_rating.enums.Genre;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Genre")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreId;

    @Enumerated(EnumType.STRING)
    private Genre genre;


    @ManyToMany(mappedBy = "genreList")
    private List<Movie> movieList;


    @OneToMany(mappedBy="genre")
    private List<DailyGenreSummary> dailyGenreSummaryList;


    public GenreEntity(){}

    public GenreEntity(Genre genre){
        this.genre=genre;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }


}
