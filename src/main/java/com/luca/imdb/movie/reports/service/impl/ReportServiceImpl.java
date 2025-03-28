package com.luca.imdb.movie.reports.service.impl;

import com.luca.imdb.movie.reports.config.ExecutionProperties;
import com.luca.imdb.movie.reports.dto.DailySummaryDto;
import com.luca.imdb.movie.reports.dto.DailySummaryGenreDto;
import com.luca.imdb.movie.reports.dto.TrendingMovieDto;
import com.luca.imdb.movie.reports.util.FormatUtils;
import com.luca.imdb.movie.reports.util.ReportUtils;
import org.springframework.stereotype.Service;
import com.luca.imdb.movie.reports.service.ReportService;


import java.util.List;


import static com.luca.imdb.movie.reports.util.ReportUtils.*;

@Service
public class ReportServiceImpl implements ReportService {

    private final ExecutionProperties executionProperties;

    public ReportServiceImpl(ExecutionProperties executionProperties) {
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

        return String.join(",", genre, dto.getNumMoviesAnalyzed().toString(), dto.getNumNewMovies().toString(), dto.getTotalNumVotes().toString()
                , dto.getNumNewVotes().toString(), formatDouble(dto.getTotalAvgNumVotes()), formatDouble(dto.getCurrentVoteDensity()), formatDouble(dto.getAvgRating())
                , formatDouble(dto.getAvgRatingVariation()), dto.getTotalAvgDuration() != null ? formatDuration(dto.getTotalAvgDuration().intValue()) : "NA",
                dto.getNewMoviesAvgDuration() != null ? formatDuration(dto.getNewMoviesAvgDuration().intValue()) : "NA"
                , dto.getNumTotalAdultMovies().toString(), formatPercentage(dto.getTotalAdultMoviesPerc()), startDate, endDate) + "\n";
    }


}
