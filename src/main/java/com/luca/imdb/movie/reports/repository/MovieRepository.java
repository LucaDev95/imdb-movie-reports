package com.luca.imdb.movie.reports.repository;

import com.luca.imdb.movie.reports.entitiy.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface MovieRepository extends JpaRepository<Movie,Long>,MovieRepositoryExtension {


    @Modifying
    @Query("delete from Movie m where m.insertDate=:insertDate")
    public void deleteByInsertDate(@Param("insertDate") LocalDate insertDate);

    @Modifying
    @Query(value = "delete from Movie m where m.insertDate<:limitDate and m.year<:limitYear")
    public int deleteOldMovies(@Param("limitDate") LocalDate limitDate, @Param("limitYear") Integer limitYear);


    @Modifying
    @Query(value = "delete from movie_genre mg using movie mov where mg.movie_id=mov.id and mov.insert_date=:insertDate",nativeQuery = true)
    public void deleteMovieGenreByInsertDate(@Param("insertDate") LocalDate insertDate);

    @Modifying
    @Query(value = "delete from movie_genre mg using movie mov where mg.movie_id=mov.id and mov.insert_date<:limitDate and mov.year<:limitYear",nativeQuery = true)
    public int deleteOldMoviesGenre(@Param("limitDate") LocalDate limitDate, @Param("limitYear") Integer limitYear);

    
    @Modifying
    @Query(value = "truncate Movie CASCADE",nativeQuery = true)
    public void truncateMovie();


    @Modifying
    @Query(value = "truncate movie_genre",nativeQuery = true)
    public void truncateMovieGenre();
}
