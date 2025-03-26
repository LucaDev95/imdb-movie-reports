package com.luca.imdb_movie_rating.service.impl;

import com.luca.imdb_movie_rating.config.ExecutionProperties;
import com.luca.imdb_movie_rating.dto.*;
import com.luca.imdb_movie_rating.entitiy.*;
import com.luca.imdb_movie_rating.enums.Genre;
import com.luca.imdb_movie_rating.repository.DailyGenreSummaryRepository;
import com.luca.imdb_movie_rating.repository.DailySummaryRepository;
import com.luca.imdb_movie_rating.repository.DailyTrendingMoviesSummaryRepository;
import com.luca.imdb_movie_rating.repository.GenreRepository;
import com.luca.imdb_movie_rating.service.SummaryService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SummaryServiceImpl implements SummaryService {

    private final DailyTrendingMoviesSummaryRepository dailyTrendingMoviesRepository;

    private final EntityManager entityManager;

    private final DailySummaryRepository dailySummaryRepository;

    private final GenreRepository genreRepository;

    private final DailyGenreSummaryRepository dailyGenreSummaryRepository;

    private final ExecutionProperties executionProperties;

    public SummaryServiceImpl(DailyTrendingMoviesSummaryRepository dailyTrendingMoviesRepository, EntityManager entityManager
    ,DailySummaryRepository dailySummaryRepository,GenreRepository genreRepository,DailyGenreSummaryRepository dailyGenreSummaryRepository,ExecutionProperties executionProperties){
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

        Map<Genre,Double> avgOverallRuntimeMinutes=collectDoubleMap(dailyGenreSummaryRepository.getAvgTotalRuntimeMinutes());

        Map<Genre,Double> avgDailyRuntimeMinutes=collectDoubleMap(dailyGenreSummaryRepository.getAvgRuntimeMinutesByDate(executionProperties.getStartDate()));

        Map<Genre,Long> totalNumAdultMovies=collectLongMap(dailyGenreSummaryRepository.getTotalNumAdultMovies());

        Map<Genre,Long> newMovies = collectLongMap(dailyGenreSummaryRepository.getNewMovies(executionProperties.getStartDate()));

        Map<Genre,Long> numValuationMovies=collectLongMap(dailyGenreSummaryRepository.getMoviesAnalyzed());

        Map<Genre,Double> todayAvgRating=collectDoubleMap(dailyGenreSummaryRepository.getAvgRatingByDate(executionProperties.getCurrentDate()));

        Map<Genre,Double> yesterdayAvgRating=collectDoubleMap(dailyGenreSummaryRepository.getAvgRatingByDate(executionProperties.getStartDate()));

        Map<Genre,Double> overallAvgRating= collectDoubleMap(dailyGenreSummaryRepository.getTotalAvgRating());

        Map<Genre,Long> sumVotesUntilToday = collectLongMap(dailyGenreSummaryRepository.getSumNumVotesUntilDate(executionProperties.getCurrentDate()));

        Map<Genre,Long> sumVotesUntilYesterday = collectLongMap(dailyGenreSummaryRepository.getSumNumVotesUntilDate(executionProperties.getStartDate()));

        
            Long todayNumVotes=sumVotesUntilToday-sumVotesUntilYesterday;

            dto.setTodayNumMovies(newMovies);
            dto.setAvgRating(overallAvgRating);
            dto.setMoviesValuated(numValuationMovies);

            dto.setAvgRatingVariation(todayAvgRating-yesterdayAvgRating);

            dto.setOverallNumAdultMovies(totalNumAdultMovies);
            dto.setOverallAvgRuntimeMinutes(avgOverallRuntimeMinutes);
            dto.setCurrentVoteDensity((double)todayNumVotes/numValuationMovies);

            dto.setTodayAvgRuntimeMinutes(avgDailyRuntimeMinutes);

            dto.setTodayNumAdultMovies(dailyNumAdultMovies);


            dto.setTotalNumVotes(sumVotesUntilToday);

            dto.setTodayNumVotes(todayNumVotes);
            dto.setValuationStartDate(yesterday);
            dto.setValuationDate(today);

            dto.setTotalAvgNumVotes((double)sumVotesUntilToday/numValuationMovies);
            dto.setOverallAdultMoviesPerc(((double)totalNumAdultMovies/numValuationMovies) *100);
            dto.setTodayAdultMoviesPerc(((double)dailyNumAdultMovies/newMovies)*100);

            dto.setGenre(g.getGenre());

            genreDtoList.add(dto);




        return genreDtoList;

    }


    @Override
    @Transactional(readOnly = true)
    public List<TrendingMovieDto> getDailyTrendingMoviesSummary() {
        return dailyTrendingMoviesRepository.findDailyTrendingMoviesSummary(executionProperties.getStartDate(),executionProperties.getCurrentDate());
    }

    @Override
    @Transactional
    public void saveDailyTrendingMovies(List<TrendingMovieDto> trendingMoviesList) {

        List<DailyTrendingMoviesSummary> entityList=  trendingMoviesList.stream().map(t->{
            DailyTrendingMoviesSummary e=new DailyTrendingMoviesSummary();

            Movie movie = entityManager.getReference(Movie.class, t.getMovieId());
            Rating startRating = entityManager.getReference(Rating.class, t.getStartRatingId());
            Rating endRating = entityManager.getReference(Rating.class, t.getEndRatingId());


            e.setMovie(movie);
            e.setStartRatig(startRating);
            e.setEndRating(endRating);
            e.setPosition(t.getPosition());
            return e;
        }).toList();

        dailyTrendingMoviesRepository.saveAll(entityList);
    }

    @Override
    @Transactional
    public void saveDailySummary(DailySummaryDto dailySummaryDto) {

        LocalDate today=LocalDate.now();

        DailySummary dailySummary=new DailySummary();
        dailySummary.setAvgRating(dailySummaryDto.getAvgRating());
        dailySummary.setNumMoviesAnalyzed(dailySummaryDto.getNumMoviesAnalyzed());

        dailySummary.setAvgRatingVariation(dailySummaryDto.getAvgRatingVariation());
        dailySummary.setAvgRating(dailySummaryDto.getAvgRating());
        dailySummary.setTotalAvgDuration(dailySummaryDto.getTotalAvgDuration());

        dailySummary.setNumTotalAdultMovies(dailySummaryDto.getNumTotalAdultMovies());

        dailySummary.setNumTotalVotes(dailySummaryDto.getTotalNumVotes());
        dailySummary.setTotalAvgNumVotes(dailySummaryDto.getTotalAvgNumVotes());

        dailySummary.setNumNewMovies(dailySummaryDto.getNumNewMovies());
        dailySummary.setNumNewVotes(dailySummaryDto.getNumNewVotes());

        dailySummary.setCurrentVoteDensity(dailySummaryDto.getCurrentVoteDensity());
        dailySummary.setNewMoviesAvgDuration(dailySummaryDto.getNewMoviesAvgDuration());

        dailySummary.setTotalAdultMoviesPerc(dailySummaryDto.getTotalAdultMoviesPerc());

        dailySummaryRepository.save(dailySummary);

    }

    @Override
    @Transactional
    public void saveDailySummaryGenreList(List<DailySummaryGenreDto> dailySummaryDtoList) {

        LocalDate today=LocalDate.now();
        LocalDate yesterday=today.minusDays(1);

        List<DailyGenreSummary> list=dailySummaryDtoList.stream().map(dailySummaryDto->{
                DailyGenreSummary dailySummary=new DailyGenreSummary();


            dailySummary.setAvgRating(dailySummaryDto.getAvgRating());
            dailySummary.setMoviesValuated(dailySummaryDto.getMoviesValuated());

            dailySummary.setAvgRatingVariation(dailySummaryDto.getAvgRatingVariation());
            dailySummary.setAvgRating(dailySummaryDto.getAvgRating());
            dailySummary.setOverallAvgRuntimeMinutes(dailySummaryDto.getOverallAvgRuntimeMinutes());

            dailySummary.setOverallNumAdultMovies(dailySummaryDto.getOverallNumAdultMovies());
            dailySummary.setTodayNumAdultMovies(dailySummaryDto.getTodayNumAdultMovies());
            dailySummary.setValuationStartDate(yesterday);
            dailySummary.setValuationDate(today);

            dailySummary.setTotalNumVotes(dailySummaryDto.getTotalNumVotes());
            dailySummary.setTotalAvgNumVotes(dailySummaryDto.getTotalAvgNumVotes());

            dailySummary.setTodayNumMovies(dailySummaryDto.getTodayNumMovies());
            dailySummary.setTodayNumVotes(dailySummaryDto.getTodayNumVotes());

            dailySummary.setCurrentVoteDensity(dailySummaryDto.getCurrentVoteDensity());
            dailySummary.setTodayAvgRuntimeMinutes(dailySummaryDto.getTodayAvgRuntimeMinutes());

            dailySummary.setOverallAdultMoviesPerc(dailySummaryDto.getOverallAdultMoviesPerc());
            dailySummary.setTodayAdultMoviesPerc(dailySummaryDto.getTodayAdultMoviesPerc());


           GenreEntity genreEntity= genreRepository.findByGenre(dailySummaryDto.getGenre()).orElseThrow(()->new RuntimeException("genre not found"));

            dailySummary.setGenre(genreEntity);


                return dailySummary;

        }).toList();

        dailyGenreSummaryRepository.saveAll(list);

    }

    @Override
    @Transactional
    public void deleteTodaySummary() {

        LocalDate today=LocalDate.now();

        dailyTrendingMoviesRepository.deleteByEndRatingDate(today);

        dailySummaryRepository.deleteByEndRatingDate(today);
        dailyGenreSummaryRepository.deleteByEndRatingDate(today);
    }

    private Map<Genre,Double> collectDoubleMap(List<GenreSummaryDoubleResult> resultList){
        return resultList.stream().collect(Collectors.toMap(GenreSummaryResult::getGenre, GenreSummaryDoubleResult::getValue,(o1,o2)->o1,()->new EnumMap<>(Genre.class)));
    }

    private Map<Genre,Long> collectLongMap(List<GenreSummaryLongResult> resultList){
        return resultList.stream().collect(Collectors.toMap(GenreSummaryResult::getGenre, GenreSummaryLongResult::getValue,(o1,o2)->o1,()->new EnumMap<>(Genre.class)));
    }

}
