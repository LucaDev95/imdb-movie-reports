select gen.genre,t2.* from(select ROW_NUMBER() over () as movie_position,t1.* from( select mov.t_const as tConst,mov.primary_title as primaryTitle,mov.original_title as origTitle,
            mov.is_adult ,mov.year, mov.runtime_minutes,
            endR.num_votes-startR.num_votes as numVotesVariation,
            endR.num_votes as currentNumVotes,
            endR.average_rating-startR.average_rating as avgRatingVariation,
            endR.average_rating as currentAvgRating,
            mov.id as movie_id,
            endR.id as end_rating_id,
            startR.id as start_rating_id
            from movie mov
            inner join (select id,movie_id,num_votes,average_rating from  rating where rating.insert_date=:'startDate') as startR
            on mov.id = startR.movie_id
            inner join (select id,movie_id,num_votes,average_rating from  rating where insert_date=:'endDate') as endR
            on mov.id=endR.movie_id
            order by numVotesVariation desc limit 100)
			t1 ) t2 inner join movie_genre mg
			on mg.movie_id=t2.movie_id
			inner join genre gen
			on mg.genre_id=gen.genre_id order by t2.movie_position