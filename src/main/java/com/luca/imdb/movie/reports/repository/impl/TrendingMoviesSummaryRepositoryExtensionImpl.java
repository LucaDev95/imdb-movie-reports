package com.luca.imdb.movie.reports.repository.impl;

import com.luca.imdb.movie.reports.dto.TrendingMovieDto;
import com.luca.imdb.movie.reports.enums.Genre;
import com.luca.imdb.movie.reports.repository.TrendingMoviesSummaryRepositoryExtension;
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


public class TrendingMoviesSummaryRepositoryExtensionImpl implements TrendingMoviesSummaryRepositoryExtension {

    private static final Logger logger = LoggerFactory.getLogger(TrendingMoviesSummaryRepositoryExtensionImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private Resource trendingMoviesQueryResource;

    public TrendingMoviesSummaryRepositoryExtensionImpl(@Value("classpath:queries/trendingMoviesQuery.sql")Resource trendingMoviesQueryResource, NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
        this.trendingMoviesQueryResource=trendingMoviesQueryResource;
    }

    @Override
    public List<TrendingMovieDto> findTrendingMoviesSummary(LocalDate startDate,LocalDate currentDate) throws IOException {

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("startDate",startDate);
        params.addValue("endDate",currentDate);

        String  trendingMoviesQuery=new String(trendingMoviesQueryResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        logger.info("executing query : {}",trendingMoviesQuery);

        Map<Long,TrendingMovieDto> trendingMoviesMap=new LinkedHashMap<>();

        jdbcTemplate.query(trendingMoviesQuery,params,(rs, rowNum)->{

            Long movieId=rs.getLong("movie_id");
            String genreStr=rs.getString("genre_name");
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
