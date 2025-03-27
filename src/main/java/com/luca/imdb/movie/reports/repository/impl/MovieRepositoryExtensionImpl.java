package com.luca.imdb.movie.reports.repository.impl;

import com.luca.imdb.movie.reports.config.ExecutionProperties;
import com.luca.imdb.movie.reports.dto.MovieIdAndTConst;
import com.luca.imdb.movie.reports.repository.MovieRepositoryExtension;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class MovieRepositoryExtensionImpl implements MovieRepositoryExtension {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ExecutionProperties executionProperties;

    private final String ID_AND_T_CONST="select id,t_const from movie mov order by mov.t_const rows fetch next :groupNum rows only";

    private final String NEXT_ID_AND_T_CONST="select id,t_const from movie mov where mov.t_const>:tConst order by mov.t_const rows fetch next :groupNum rows only";

    public MovieRepositoryExtensionImpl( NamedParameterJdbcTemplate jdbcTemplate,ExecutionProperties executionProperties){
        this.jdbcTemplate=jdbcTemplate;
        this.executionProperties=executionProperties;
    }


    @Override
    public List<MovieIdAndTConst> findMovieIdAndTConst(String tConst) {

        MapSqlParameterSource params = new MapSqlParameterSource();

        String query=ID_AND_T_CONST;

        params.addValue("groupNum",executionProperties.gettConstSelectSize());

        if(tConst!=null){
            query=NEXT_ID_AND_T_CONST;
            params.addValue("tConst",tConst);
        }

        List<MovieIdAndTConst> movieIdAndTConstList=jdbcTemplate.query(query,params,(rs, rowNum)->{
            MovieIdAndTConst idAndTConst=new MovieIdAndTConst(rs.getLong("id"),rs.getString("t_Const"));
           return idAndTConst;
        });
        return movieIdAndTConstList;
    }
}
