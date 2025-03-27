package com.luca.imdb.movie.reports.service.impl;


import com.luca.imdb.movie.reports.dto.*;
import com.luca.imdb.movie.reports.entitiy.*;
import com.luca.imdb.movie.reports.service.SummaryService;
import com.luca.imdb.movie.reports.config.ExecutionProperties;

import com.luca.imdb.movie.reports.enums.Genre;
import com.luca.imdb.movie.reports.exception.ReportException;
import com.luca.imdb.movie.reports.repository.GenreSummaryRepository;
import com.luca.imdb.movie.reports.repository.SummaryRepository;
import com.luca.imdb.movie.reports.repository.TrendingMoviesSummaryRepository;
import com.luca.imdb.movie.reports.repository.GenreRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SummaryServiceImpl implements SummaryService {

    private final TrendingMoviesSummaryRepository dailyTrendingMoviesRepository;

    private final EntityManager entityManager;

    private final SummaryRepository dailySummaryRepository;

    private final GenreRepository genreRepository;

    private final GenreSummaryRepository dailyGenreSummaryRepository;

    private final ExecutionProperties executionProperties;

    public SummaryServiceImpl(TrendingMoviesSummaryRepository dailyTrendingMoviesRepository, EntityManager entityManager
    , SummaryRepository dailySummaryRepository, GenreRepository genreRepository, GenreSummaryRepository dailyGenreSummaryRepository, ExecutionProperties executionProperties){
        this.dailyTrendingMoviesRepository=dailyTrendingMoviesRepository;

        this.entityManager=entityManager;
        this.dailySummaryRepository=dailySummaryRepository;
        this.genreRepository=genreRepository;
        this.dailyGenreSummaryRepository=dailyGenreSummaryRepository;
        this.executionProperties=executionProperties;
    }


    @Override
    @Transactional(readOnly = true)
    public DailySummaryDto calculateDailySummary() {

        DailySummaryDto dto=new DailySummaryDto();

        Double avgOverallRuntimeMinutes=dailySummaryRepository.getAvgTotalRuntimeMinutes();
        Double avgDailyRuntimeMinutes=dailySummaryRepository.getAvgRuntimeMinutesByDate(executionProperties.getStartDate());

        Long totalNumAdultMovies=dailySummaryRepository.getTotalNumAdultMovies();

        Long newMovies = dailySummaryRepository.getNewMovies(executionProperties.getStartDate());

        Long numValuationMovies=dailySummaryRepository.getAnalyzedMovies();

        Double todayAvgRating=dailySummaryRepository.getAvgRatingByDate(executionProperties.getCurrentDate());

        Double yesterdayAvgRating=dailySummaryRepository.getAvgRatingByDate(executionProperties.getStartDate());

        Double overallAvgRating= dailySummaryRepository.getTotalAvgRating();

        Long sumVotesUntilToday = dailySummaryRepository.getSumNumVotesUntilDate(executionProperties.getCurrentDate());

        Long sumVotesUntilStartDate = dailySummaryRepository.getSumNumVotesUntilDate(executionProperties.getStartDate());

        Long todayNumVotes=sumVotesUntilToday-sumVotesUntilStartDate;

        dto.setNumNewMovies(newMovies);
        dto.setAvgRating(overallAvgRating);
        dto.setNumMoviesAnalyzed(numValuationMovies);

        dto.setAvgRatingVariation(todayAvgRating-yesterdayAvgRating);

        dto.setNumTotalAdultMovies(totalNumAdultMovies);

        dto.setTotalAvgDuration(avgOverallRuntimeMinutes);

        dto.setCurrentVoteDensity((double)todayNumVotes/numValuationMovies);

        dto.setNewMoviesAvgDuration(avgDailyRuntimeMinutes);

        dto.setTotalNumVotes(sumVotesUntilToday);

        dto.setNumNewVotes(todayNumVotes);

        dto.setTotalAvgNumVotes((double)sumVotesUntilToday/numValuationMovies);
        dto.setTotalAdultMoviesPerc(((double)totalNumAdultMovies/numValuationMovies) *100);

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailySummaryGenreDto> calculateDailySummaryByGenre() {


        List<DailySummaryGenreDto> genreDtoList=new ArrayList<>();

        Map<Genre,DailySummaryGenreDto> summaryGenreMap= Arrays.stream(Genre.values()).collect(Collectors.toMap(g->g, DailySummaryGenreDto::new,(o1, o2)->o1,()->new EnumMap<>(Genre.class)));

        Map<Genre,Double> totalAvgDuration= collectDoubleMap(dailyGenreSummaryRepository.getAvgTotalRuntimeMinutes());


        Map<Genre,Double> newMoviesAvgDuration=collectDoubleMap(dailyGenreSummaryRepository.getAvgRuntimeMinutesByDate(executionProperties.getStartDate()));

        Map<Genre,Long> totalNumAdultMovies=collectLongMap(dailyGenreSummaryRepository.getTotalNumAdultMovies());

        Map<Genre,Long> newMovies = collectLongMap(dailyGenreSummaryRepository.getNewMovies(executionProperties.getStartDate()));

        Map<Genre,Long> numValuationMovies=collectLongMap(dailyGenreSummaryRepository.getMoviesAnalyzed());

        Map<Genre,Double> todayAvgRating=collectDoubleMap(dailyGenreSummaryRepository.getAvgRatingByDate(executionProperties.getCurrentDate()));

        Map<Genre,Double> yesterdayAvgRating=collectDoubleMap(dailyGenreSummaryRepository.getAvgRatingByDate(executionProperties.getStartDate()));

        Map<Genre,Double> overallAvgRating= collectDoubleMap(dailyGenreSummaryRepository.getTotalAvgRating());

        Map<Genre,Long> sumVotesUntilToday = collectLongMap(dailyGenreSummaryRepository.getSumNumVotesUntilDate(executionProperties.getCurrentDate()));

        Map<Genre,Long> sumVotesUntilYesterday = collectLongMap(dailyGenreSummaryRepository.getSumNumVotesUntilDate(executionProperties.getStartDate()));


        for(Genre genre:summaryGenreMap.keySet()){
            DailySummaryGenreDto dto= summaryGenreMap.get(genre);

            dto.setTotalAvgDuration(totalAvgDuration.get(genre));

           dto.setNewMoviesAvgDuration(newMoviesAvgDuration.get(genre));

           dto.setNumTotalAdultMovies(totalNumAdultMovies.get(genre));

            dto.setNumNewMovies(newMovies.get(genre));

            dto.setNumMoviesAnalyzed(numValuationMovies.get(genre));

            dto.setAvgRatingVariation(todayAvgRating.get(genre)-yesterdayAvgRating.get(genre));

            dto.setAvgRating(overallAvgRating.get(genre));


            dto.setNumNewVotes(sumVotesUntilToday.get(genre)-sumVotesUntilYesterday.get(genre));


            dto.setCurrentVoteDensity((double)dto.getNumNewVotes()/dto.getNumMoviesAnalyzed());

            dto.setTotalAvgNumVotes((double)sumVotesUntilToday.get(genre)/dto.getNumMoviesAnalyzed());

            dto.setTotalAdultMoviesPerc(((double)dto.getNumTotalAdultMovies()/dto.getNumMoviesAnalyzed())*100);

        }

        

        return summaryGenreMap.values().stream().toList();

    }


    @Override
    @Transactional(readOnly = true)
    public List<TrendingMovieDto> getDailyTrendingMoviesSummary() throws IOException {
        return dailyTrendingMoviesRepository.findTrendingMoviesSummary(executionProperties.getStartDate(),executionProperties.getCurrentDate());
    }

    @Override
    @Transactional
    public void saveDailyTrendingMovies(List<TrendingMovieDto> trendingMoviesList) {

        List<TrendingMoviesSummary> entityList=  trendingMoviesList.stream().map(t->{
            TrendingMoviesSummary e=new TrendingMoviesSummary();

            Movie movie = entityManager.getReference(Movie.class, t.getMovieId());
            Rating startRating = entityManager.getReference(Rating.class, t.getStartRatingId());
            Rating endRating = entityManager.getReference(Rating.class, t.getEndRatingId());


            e.setMovie(movie);
            e.setStartRating(startRating);
            e.setEndRating(endRating);
            e.setPosition(t.getPosition());
            return e;
        }).toList();

        dailyTrendingMoviesRepository.saveAll(entityList);
    }

    @Override
    @Transactional
    public void saveDailySummary(DailySummaryDto dailySummaryDto,List<DailySummaryGenreDto> dailySummaryGenreDtoList) {


        Summary dailySummary=new Summary();

        dailySummary.setStartDate(executionProperties.getStartDate());

        dailySummary.setEndDate(executionProperties.getCurrentDate());

        addFieldsToSummaryEntity(dailySummaryDto,dailySummary);

        List<GenreSummary> dtoList=dailySummaryGenreDtoList.stream().map(dailyGenreSummaryDto->{
            GenreSummary dailyGenreSummary=new GenreSummary();

            addFieldsToSummaryEntity(dailyGenreSummaryDto,dailyGenreSummary);

            GenreEntity genreEntity= genreRepository.findByGenreName(dailyGenreSummaryDto.getGenre()).orElseThrow(()->new ReportException(String.format("Genre %s not found",dailyGenreSummaryDto.getGenre())));

            dailyGenreSummary.setGenre(genreEntity);


            return dailyGenreSummary;

        }).toList();

        dailySummary.setGenreSummaryList(dtoList);

        dailySummaryRepository.save(dailySummary);

    }


    @Override
    @Transactional
    public void deleteTodaySummary() {

        dailyTrendingMoviesRepository.deleteByEndRatingDate(executionProperties.getCurrentDate());

        Summary summary=dailySummaryRepository.findByEndDate(executionProperties.getCurrentDate());

        dailySummaryRepository.delete(summary);

    }

    private Map<Genre,Double> collectDoubleMap(List<GenreSummaryDoubleResult> resultList){
        return resultList.stream().collect(Collectors.toMap(GenreSummaryResult::getGenre, GenreSummaryDoubleResult::getValue,(o1, o2)->o1,()->new EnumMap<>(Genre.class)));
    }

    private Map<Genre,Long> collectLongMap(List<GenreSummaryLongResult> resultList){
        return resultList.stream().collect(Collectors.toMap(GenreSummaryResult::getGenre, GenreSummaryLongResult::getValue,(o1,o2)->o1,()->new EnumMap<>(Genre.class)));
    }

    private void addFieldsToSummaryEntity(DailySummaryDto dto, AbstractSummary entity){
        entity.setAvgRating(dto.getAvgRating());
        entity.setNumMoviesAnalyzed(dto.getNumMoviesAnalyzed());

        entity.setAvgRatingVariation(dto.getAvgRatingVariation());
        entity.setAvgRating(dto.getAvgRating());
        entity.setTotalAvgDuration(dto.getTotalAvgDuration());

        entity.setNumTotalAdultMovies(dto.getNumTotalAdultMovies());

        entity.setNumTotalVotes(dto.getTotalNumVotes());
        entity.setTotalAvgNumVotes(dto.getTotalAvgNumVotes());

        entity.setNumNewMovies(dto.getNumNewMovies());
        entity.setNumNewVotes(dto.getNumNewVotes());

        entity.setCurrentVoteDensity(dto.getCurrentVoteDensity());
        entity.setNewMoviesAvgDuration(dto.getNewMoviesAvgDuration());

        entity.setTotalAdultMoviesPerc(dto.getTotalAdultMoviesPerc());
    }

}
