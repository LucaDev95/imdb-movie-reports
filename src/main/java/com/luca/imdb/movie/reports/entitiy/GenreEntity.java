package com.luca.imdb.movie.reports.entitiy;

import com.luca.imdb.movie.reports.enums.Genre;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Genre")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Genre genreName;


    @ManyToMany(mappedBy = "genreList")
    private List<Movie> movieList;


    @OneToMany(mappedBy="genre")
    private List<GenreSummary> genreSummaryList;


    public GenreEntity(){}

    public GenreEntity(Genre genreName){
        this.genreName=genreName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Genre getGenreName() {
        return genreName;
    }

    public void setGenreName(Genre genreName) {
        this.genreName = genreName;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<GenreSummary> getGenreSummaryList() {
        return genreSummaryList;
    }

    public void setGenreSummaryList(List<GenreSummary> genreSummaryList) {
        this.genreSummaryList = genreSummaryList;
    }
}
