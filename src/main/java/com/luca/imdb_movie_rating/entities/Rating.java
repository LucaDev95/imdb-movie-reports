package com.luca.imdb_movie_rating.entities;

import com.luca.imdb_movie_rating.dtos.RatingResult;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@SqlResultSetMapping(
        name = "RatingResultMapping",
        classes = @ConstructorResult(
                targetClass = RatingResult.class,
                columns = {
                        @ColumnResult(name = "tConst", type = String.class),
                        @ColumnResult(name = "primaryTitle", type = String.class),
                        @ColumnResult(name = "origTitle", type = String.class),
                        @ColumnResult(name = "numVotesDiff", type = Integer.class),
                        @ColumnResult(name = "currentNumVotes", type = Integer.class),
                        @ColumnResult(name = "avgRatingDiff", type = Float.class),
                        @ColumnResult(name = "currentAvgRating", type = Float.class),
                        @ColumnResult(name = "isAdult", type = Boolean.class),
                        @ColumnResult(name = "genres", type = String.class),
                        @ColumnResult(name = "year", type = Integer.class),
                        @ColumnResult(name = "runtimeMinutes", type = Integer.class)


                }
        )
)
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVIE_RATING")
    @SequenceGenerator(name = "MOVIE_RATING", sequenceName = "MOVIE_RATING", allocationSize = 10)
    private Long id;

    private Float averageRating;

    private Integer numVotes;

    @ManyToOne(targetEntity = Movie.class,fetch = FetchType.EAGER)
    @JoinColumn(name="movie_id",referencedColumnName = "id",nullable = false)
    private Movie movie;

    @CreationTimestamp
    private LocalDateTime insertionTime;


    public Long getId() {
        return id;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }


    public LocalDateTime getInsertionTime() {
        return insertionTime;
    }

}
