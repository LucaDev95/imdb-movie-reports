package com.luca.imdb_movie_rating.repository.impl;

import com.luca.imdb_movie_rating.dto.TrendingMovieDto;
import com.luca.imdb_movie_rating.enums.Genre;
import com.luca.imdb_movie_rating.repository.DailyTrendingMoviesSummaryRepositoryExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class SummaryRepositoryExtensionImpl implements DailyTrendingMoviesSummaryRepositoryExtension {

    private static final Logger logger = LoggerFactory.getLogger(SummaryRepositoryExtensionImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private Resource trendingMoviesQueryResource;


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



    public SummaryRepositoryExtensionImpl(@Value("classpath:queries/trendingMoviesQuery.sql")Resource trendingMoviesQueryResource,NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
        this.trendingMoviesQueryResource=trendingMoviesQueryResource;
    }


    @Override
    public List<TrendingMovieDto> findDailyTrendingMoviesSummary(LocalDate startDate,LocalDate currentDate) throws IOException {

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("startDate",startDate);
        params.addValue("endDate",currentDate);

        String  trendingMoviesQuery=new String(trendingMoviesQueryResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        logger.info("executing query : {}",trendingMoviesQuery);

        Map<Long,TrendingMovieDto> trendingMoviesMap=new LinkedHashMap<>();

        jdbcTemplate.query(DAILY_TRENDING_MOVIES,params,(rs, rowNum)->{

            Long movieId=rs.getLong("movie_id");
            String genreStr=rs.getString("genre");
            Genre genre=Genre.valueOf(genreStr);

            TrendingMovieDto trendingMovieDto = trendingMoviesMap.computeIfAbsent(movieId, id -> {

                try {
                    TrendingMovieDto dto = new TrendingMovieDto();
                    dto.setMovieId(id);
                    dto.setStartRatingId(rs.getLong("start_rating_id"));
                    dto.setEndRatingId(rs.getLong("end_rating_id"));
                    dto.setAvgRatingDiff(rs.getDouble("avgRatingVariation"));
                    dto.setCurrentAvgRating(rs.getDouble("currentAvgRating"));
                    dto.setCurrentNumVotes(rs.getInt("currentNumVotes"));
                    dto.setNumVotesDiff(rs.getInt("numVotesVariation"));
                    dto.setYear(rs.getInt("year"));
                    dto.settConst(rs.getString("tConst"));
                    dto.setRuntimeMinutes(rs.getInt("runtime_minutes"));
                    dto.setPrimaryTitle(rs.getString("primaryTitle"));
                    dto.setOrigTitle(rs.getString("origTitle"));
                    dto.setAdult(rs.getBoolean("is_adult"));
                    dto.setPosition(rs.getInt("movie_position"));
                    return dto;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });

            trendingMovieDto.addGenre(genre);


            return null;
        });

        return new ArrayList<>(trendingMoviesMap.values());

    }

}
