package com.luca.imdb_movie_rating.service.impl;

import com.luca.imdb_movie_rating.config.ExecutionProperties;
import com.luca.imdb_movie_rating.dto.DailySummaryDto;
import com.luca.imdb_movie_rating.dto.DailySummaryGenreDto;
import com.luca.imdb_movie_rating.dto.TrendingMovieDto;


import com.luca.imdb_movie_rating.service.RatingService;
import com.luca.imdb_movie_rating.util.FormatUtils;
import com.luca.imdb_movie_rating.util.ReportUtils;
import org.springframework.stereotype.Service;
import com.luca.imdb_movie_rating.service.ReportService;


import java.util.List;


import static com.luca.imdb_movie_rating.util.ReportUtils.*;

@Service
public class ReportServiceImpl implements ReportService {


    private final RatingService ratingService;

    private final ExecutionProperties executionProperties;

    public ReportServiceImpl(RatingService ratingService,ExecutionProperties executionProperties) {
        this.ratingService = ratingService;
        this.executionProperties=executionProperties;
    }


    @Override
    public String generateTrendingMoviesReport(List<TrendingMovieDto> trendingMovieDtoList) {

        StringBuilder sb = new StringBuilder();


        sb.append(ReportUtils.createTrendingMoviesHeader());

        trendingMovieDtoList.forEach(movie -> {
            String line = String.join(",", movie.getPosition().toString(), movie.gettConst(), escapeString(movie.getPrimaryTitle())
                    , escapeString(movie.getOrigTitle()), movie.getNumVotesDiff().toString(), movie.getCurrentNumVotes().toString(), formatDouble(movie.getAvgRatingDiff()),
                    formatDouble(movie.getCurrentAvgRating()), movie.getYear().toString(), formatDuration(movie.getRuntimeMinutes()), parseBoolean(movie.getAdult())
                    , formatGenreList(movie.getGenreList()),FormatUtils.formatDate(executionProperties.getStartDate()),FormatUtils.formatDate(executionProperties.getCurrentDate())) + "\n";


            sb.append(line);
        });


        return sb.toString();

    }

    @Override
    public String generateDailySummaryReport(DailySummaryDto dailySummary, List<DailySummaryGenreDto> dailyGenreSummaryList) {


        StringBuilder sb = new StringBuilder();

        sb.append(createDailySummaryHeader());

        String startDate = FormatUtils.formatDate(executionProperties.getStartDate());
        String endDate = FormatUtils.formatDate(executionProperties.getCurrentDate());


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
