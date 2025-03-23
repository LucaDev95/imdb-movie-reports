package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.dtos.DailySummaryDto;
import com.luca.imdb_movie_rating.dtos.DailySummaryGenreDto;
import com.luca.imdb_movie_rating.dtos.TrendingMovieDto;


import com.luca.imdb_movie_rating.services.RatingService;
import com.luca.imdb_movie_rating.utils.FormatUtils;
import com.luca.imdb_movie_rating.utils.ReportUtils;
import org.springframework.stereotype.Service;
import com.luca.imdb_movie_rating.services.ReportService;


import java.util.List;


import static com.luca.imdb_movie_rating.utils.ReportUtils.*;

@Service
public class ReportServiceImpl implements ReportService {


    private final RatingService ratingService;

    public ReportServiceImpl(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @Override
    public String generateTrendingMoviesReport(List<TrendingMovieDto> trendingMovieDtoList) {

        StringBuilder sb = new StringBuilder();


        sb.append(ReportUtils.createTrendingMoviesHeader());

        trendingMovieDtoList.forEach(movie -> {
            String line = String.join(",", movie.getPosition().toString(), movie.gettConst(), escapeString(movie.getPrimaryTitle())
                    , escapeString(movie.getOrigTitle()), movie.getNumVotesDiff().toString(), movie.getCurrentNumVotes().toString(), formatDouble(movie.getAvgRatingDiff()),
                    formatDouble(movie.getCurrentAvgRating()), movie.getYear().toString(), formatDuration(movie.getRuntimeMinutes()), parseBoolean(movie.getAdult())
                    , movie.getGenreList()) + "\n";


            sb.append(line);
        });


        return sb.toString();

    }

    @Override
    public String generateDailySummaryReport(DailySummaryDto dailySummary, List<DailySummaryGenreDto> dailyGenreSummaryList) {


        StringBuilder sb = new StringBuilder();

        sb.append(createDailySummaryHeader());

        String startDate = FormatUtils.formatDate(dailySummary.getValuationStartDate());
        String endDate = FormatUtils.formatDate(dailySummary.getValuationDate());


        sb.append(getSummaryLine(dailySummary, "ALL", startDate, endDate));


        dailyGenreSummaryList.forEach(s -> sb.append(getSummaryLine(s, s.getGenre().name(), startDate, endDate)));


        return sb.toString();


    }

    private String getSummaryLine(DailySummaryDto dto, String genre, String startDate, String endDate) {

        return String.join(",", genre, dto.getMoviesValuated().toString(), dto.getNumNewMovies().toString(), dto.getTotalNumVotes().toString()
                , dto.getNumNewVotes().toString(), formatDouble(dto.getTotalAvgNumVotes()), formatDouble(dto.getCurrentVoteDensity()), formatDouble(dto.getAvgRating())
                , formatDouble(dto.getAvgRatingVariation()), dto.getOverallAvgRuntimeMinutes() != null ? formatDuration(dto.getOverallAvgRuntimeMinutes().intValue()) : "NA",
                dto.getNewMoviesAvgDuration() != null ? formatDuration(dto.getNewMoviesAvgDuration().intValue()) : "NA"
                , dto.getTodayNumAdultMovies().toString(), formatPercentage(dto.getOverallAdultMoviesPerc()), startDate, endDate) + "\n";
    }


}
