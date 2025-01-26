package com.luca.imdb_movie_rating.repositories.impl;

import com.luca.imdb_movie_rating.dtos.RatingResult;
import com.luca.imdb_movie_rating.repositories.RatingRepositoryExtension;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RatingRepositoryExtensionImpl implements RatingRepositoryExtension {

    private EntityManager em;

    public RatingRepositoryExtensionImpl(EntityManager em){
        this.em=em;
    }

    @Override
    public List<RatingResult> findRatingResultList() {


        String query="select mov.t_const as tConst,mov.primary_title as primaryTitle,mov.original_title as origTitle,\n" +
                "t1.num_votes-y1.num_votes as numVotesDiff,\n" +
                "t1.num_votes as currentNumVotes,\n" +
                "t1.average_rating-y1.average_rating as avgRatingDiff,\n" +
                "t1.average_rating as currentAvgRating,\n" +
                "mov.is_adult as isAdult,\n" +
                "mov.genres as genres,\n" +
                "mov.year as year,\n" +
                "mov.runtime_minutes as runtimeMinutes \n" +
                "from (select movie_id,num_votes,average_rating from  rating where DATE(rating.insertion_time)=CURRENT_DATE) as t1\n" +
                "inner join (select movie_id,num_votes,average_rating from  rating where DATE(insertion_time)=CURRENT_DATE-1) as y1\n" +
                "on y1.movie_id=t1.movie_id\n" +
                "inner join movie mov\n" +
                "on mov.id=t1.movie_id\n" +
                "order by numVotesDiff desc limit 100";


        List<RatingResult> dtos = em
                .createNativeQuery(query, "RatingResultMapping")
                .getResultList();


        return dtos;

    }
}
