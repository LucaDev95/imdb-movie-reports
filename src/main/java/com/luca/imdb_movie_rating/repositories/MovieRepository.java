package com.luca.imdb_movie_rating.repositories;

import com.luca.imdb_movie_rating.dtos.MovieIdAndTConst;
import com.luca.imdb_movie_rating.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie,Long>,MovieRepositoryExtension {

    @Modifying
    @Query("delete from Movie m where m.insertDate=:insertDate")
    public void deleteByInsertDate(@Param("insertDate") LocalDate insertDate);


    @Modifying
    @Query(value = "delete from movie_genre mg inner join movie m on mg.movie_id=m.id where m.insert_date=:insertDate",nativeQuery = true)
    public void deleteMovieGenreByInsertDate(@Param("insertDate") LocalDate insertDate);


    @Query("select m.id from Movie m where m.tConst=:tConst")
    public Optional<Long> findMovieIdByTConst(@Param("tConst") String tConst);



    @Modifying
    @Query(value = "truncate Movie CASCADE",nativeQuery = true)
    public void truncateMovie();


    @Modifying
    @Query(value = "truncate daily_genre_summary",nativeQuery = true)
    public void truncateMovieGenre();
}
