package com.luca.imdb.movie.reports.dto;
import com.luca.imdb.movie.reports.enums.Genre;

public class DailySummaryGenreDto extends DailySummaryDto {

    private Genre genre;

    public DailySummaryGenreDto(Genre genre){
        this.genre=genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    @Override
    public String toString() {
        return "DailySummaryGenreDto{" +
                "totalNumVotes=" + getTotalNumVotes() +
                ", avgRating=" + getAvgRating() +
                ", avgRatingVariation=" + getAvgRatingVariation() +
                ", totalAvgNumVotes=" + getTotalAvgNumVotes() +
                ", totalAdultMoviesPerc=" + getTotalAdultMoviesPerc() +
                ", newMoviesAvgDuration=" + getNewMoviesAvgDuration() +
                ", numMoviesAnalyzed=" + getNumMoviesAnalyzed() +
                ", numTotalAdultMovies=" + getNumTotalAdultMovies() +
                ", totalAvgDuration=" + getTotalAvgDuration() +
                ", numNewMovies=" + getNumNewMovies() +
                ", currentVoteDensity=" + getCurrentVoteDensity() +
                ", numNewVotes=" + getNumNewVotes() +
                ", genre=" + getGenre() +
                '}';
    }
}
