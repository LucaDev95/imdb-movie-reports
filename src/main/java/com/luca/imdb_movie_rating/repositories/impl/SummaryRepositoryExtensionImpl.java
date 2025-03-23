package com.luca.imdb_movie_rating.repositories.impl;

import com.luca.imdb_movie_rating.dtos.TrendingMovieDto;
import com.luca.imdb_movie_rating.entities.DailySummary;
import com.luca.imdb_movie_rating.enums.Genre;
import com.luca.imdb_movie_rating.repositories.DailyTrendingMoviesSummaryRepositoryExtension;
import com.luca.imdb_movie_rating.services.impl.ResetDataServiceImpl;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


public class SummaryRepositoryExtensionImpl implements DailyTrendingMoviesSummaryRepositoryExtension {

    private static final Logger logger = LoggerFactory.getLogger(SummaryRepositoryExtensionImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;


    private final String DAILY_TRENDING_MOVIES="select ROW_NUMBER() over (),* from (select mov.t_const as tConst,mov.primary_title as primaryTitle,mov.original_title as origTitle, " +
            " mov.is_adult ,mov.year, mov.runtime_minutes, " +
            " endR.num_votes-startR.num_votes as numVotesVariation, " +
            " endR.num_votes as currentNumVotes, " +
            " endR.average_rating-startR.average_rating as avgRatingVariation, " +
            " endR.average_rating as currentAvgRating, " +
            " mov.id as movie_id, " +
            " endR.id as end_rating_id, " +
            " startR.id as start_rating_id " +
            " from movie mov " +
            " inner join (select id,movie_id,num_votes,average_rating from  rating where rating.insert_date=:startDate) as startR " +
            " on mov.id = startR.movie_id " +
            " inner join (select id,movie_id,num_votes,average_rating from  rating where insert_date=:endDate) as endR " +
            " on mov.id=endR.movie_id" +
            " order by numVotesVariation desc limit 100)";


        private final String GENRES_BY_MOVIE_ID="select genre from genre "+
            " inner join movie_genre mg "+
             " on mg.genre_id=genre.genre_id "+
                " where mg.movie_id=:movieId";



    public SummaryRepositoryExtensionImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    @Override
    public List<TrendingMovieDto> findDailyTrendingMoviesSummary(){

        MapSqlParameterSource params = new MapSqlParameterSource();

        LocalDate endDate= LocalDate.now();

        LocalDate startDate = endDate.minusDays(1);

        params.addValue("startDate",startDate);
        params.addValue("endDate",endDate);

        logger.info("executing query : {}",DAILY_TRENDING_MOVIES);


        

        List<TrendingMovieDto> trendingMovieList=jdbcTemplate.query(DAILY_TRENDING_MOVIES,params,(rs, rowNum)->{
            TrendingMovieDto trendingMovieDto=new TrendingMovieDto();
            trendingMovieDto.setMovieId(rs.getLong("movie_id"));
            trendingMovieDto.setStartRatingId(rs.getLong("start_rating_id"));
            trendingMovieDto.setEndRatingId(rs.getLong("end_rating_id"));
            trendingMovieDto.setAvgRatingDiff(rs.getFloat("avgRatingVariation"));
            trendingMovieDto.setCurrentAvgRating(rs.getFloat("currentAvgRating"));
            trendingMovieDto.setCurrentNumVotes(rs.getInt("currentNumVotes"));
            trendingMovieDto.setNumVotesDiff(rs.getInt("numVotesVariation"));
            trendingMovieDto.setYear(rs.getInt("year"));
            trendingMovieDto.settConst(rs.getString("tConst"));
            trendingMovieDto.setRuntimeMinutes(rs.getInt("runtime_minutes"));
            trendingMovieDto.setPrimaryTitle(rs.getString("primaryTitle"));
            trendingMovieDto.setOrigTitle(rs.getString("origTitle"));
            trendingMovieDto.setAdult(rs.getBoolean("is_adult"));
            trendingMovieDto.setPosition(rowNum);
            return trendingMovieDto;
        });


        for(TrendingMovieDto t : trendingMovieList){
            MapSqlParameterSource p = new MapSqlParameterSource();
            p.addValue("movieId",t.getMovieId());

            List<Genre> genreList=jdbcTemplate.query(GENRES_BY_MOVIE_ID,p,(rs,rowNum)->{
               return Genre.valueOf(rs.getString("genre")) ;
            });

            t.setGenres(genreList);
        }

        return trendingMovieList;

    }

}
