package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.dtos.DailySummaryDto;
import com.luca.imdb_movie_rating.dtos.DailySummaryGenreDto;
import com.luca.imdb_movie_rating.dtos.TrendingMovieDto;
import com.luca.imdb_movie_rating.entities.*;
import com.luca.imdb_movie_rating.repositories.DailyGenreSummaryRepository;
import com.luca.imdb_movie_rating.repositories.DailySummaryRepository;
import com.luca.imdb_movie_rating.repositories.DailyTrendingMoviesSummaryRepository;
import com.luca.imdb_movie_rating.repositories.GenreRepository;
import com.luca.imdb_movie_rating.repositories.impl.SummaryRepositoryExtensionImpl;
import com.luca.imdb_movie_rating.services.SummaryService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService {

    private final DailyTrendingMoviesSummaryRepository dailyTrendingMoviesRepository;

    private final EntityManager entityManager;

    private final DailySummaryRepository dailySummaryRepository;

    private final GenreRepository genreRepository;

    private final DailyGenreSummaryRepository dailyGenreSummaryRepository;

    public SummaryServiceImpl(DailyTrendingMoviesSummaryRepository dailyTrendingMoviesRepository, EntityManager entityManager
    ,DailySummaryRepository dailySummaryRepository,GenreRepository genreRepository,DailyGenreSummaryRepository dailyGenreSummaryRepository){
        this.dailyTrendingMoviesRepository=dailyTrendingMoviesRepository;

        this.entityManager=entityManager;
        this.dailySummaryRepository=dailySummaryRepository;
        this.genreRepository=genreRepository;
        this.dailyGenreSummaryRepository=dailyGenreSummaryRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public DailySummaryDto calculateDailySummary() {

        DailySummaryDto dto=new DailySummaryDto();

        LocalDate today=LocalDate.now();
        LocalDate yesterday=today.minusDays(1);

        Double avgOverallRuntimeMinutes=dailySummaryRepository.getAvgOverallRuntimeMinutes();
        Double avgDailyRuntimeMinutes=dailySummaryRepository.getAvgRuntimeMinutesByDate(today);

        Long totalNumAdultMovies=dailySummaryRepository.getTotalNumAdultMovies();
        Long dailyNumAdultMovies=dailySummaryRepository.getNumAdultMoviesByDate(today);

        Long newMovies = dailySummaryRepository.getNewMovies(today);

        Long numValuationMovies=dailySummaryRepository.getValuationMovies();

        Double todayAvgRating=dailySummaryRepository.getAvgRatingByDate(today);

        Double yesterdayAvgRating=dailySummaryRepository.getAvgRatingByDate(yesterday);

        Double overallAvgRating= dailySummaryRepository.getOverallAvgRating();

        Long sumVotesUntilToday = dailySummaryRepository.getSumNumVotesUntilDate(today);

        Long sumVotesUntilYesterday = dailySummaryRepository.getSumNumVotesUntilDate(yesterday);


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

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailySummaryGenreDto> calculateDailySummaryByGenre() {

        LocalDate today=LocalDate.now();
        LocalDate yesterday=today.minusDays(1);

        List<DailySummaryGenreDto> genreDtoList=new ArrayList<>();

        genreRepository.findAll().forEach(g->{
            DailySummaryGenreDto dto=new DailySummaryGenreDto();

            Double avgOverallRuntimeMinutes=dailyGenreSummaryRepository.getAvgOverallRuntimeMinutes(g.getGenreId());
            Double avgDailyRuntimeMinutes=dailyGenreSummaryRepository.getAvgRuntimeMinutesByDate(g.getGenreId(),today);

            Long totalNumAdultMovies=dailyGenreSummaryRepository.getTotalNumAdultMovies(g.getGenreId());
            Long dailyNumAdultMovies=dailyGenreSummaryRepository.getNumAdultMoviesByDate(g.getGenreId(),today);

            Long newMovies = dailyGenreSummaryRepository.getNewMovies(g.getGenreId(),today);

            Long numValuationMovies=dailyGenreSummaryRepository.getValuationMovies(g.getGenreId());

            Double todayAvgRating=dailyGenreSummaryRepository.getAvgRatingByDate(g.getGenreId(),today);

            Double yesterdayAvgRating=dailyGenreSummaryRepository.getAvgRatingByDate(g.getGenreId(),yesterday);

            Double overallAvgRating= dailyGenreSummaryRepository.getOverallAvgRating(g.getGenreId());

            Long sumVotesUntilToday = dailyGenreSummaryRepository.getSumNumVotesUntilDate(g.getGenreId(),today);

            Long sumVotesUntilYesterday = dailyGenreSummaryRepository.getSumNumVotesUntilDate(g.getGenreId(),yesterday);


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

        });


        return genreDtoList;

    }


    @Override
    @Transactional(readOnly = true)
    public List<TrendingMovieDto> getDailyTrendingMoviesSummary() {
        return dailyTrendingMoviesRepository.findDailyTrendingMoviesSummary();
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
        dailySummary.setMoviesValuated(dailySummaryDto.getMoviesValuated());

        dailySummary.setAvgRatingVariation(dailySummaryDto.getAvgRatingVariation());
        dailySummary.setAvgRating(dailySummaryDto.getAvgRating());
        dailySummary.setOverallAvgRuntimeMinutes(dailySummaryDto.getOverallAvgRuntimeMinutes());

        dailySummary.setOverallNumAdultMovies(dailySummaryDto.getOverallNumAdultMovies());
        dailySummary.setTodayNumAdultMovies(dailySummaryDto.getTodayNumAdultMovies());
        dailySummary.setValuationStartDate(today.minusDays(1));
        dailySummary.setValuationDate(today);

        dailySummary.setTotalNumVotes(dailySummaryDto.getTotalNumVotes());
        dailySummary.setTotalAvgNumVotes(dailySummaryDto.getTotalAvgNumVotes());

        dailySummary.setTodayNumMovies(dailySummaryDto.getTodayNumMovies());
        dailySummary.setTodayNumVotes(dailySummaryDto.getTodayNumVotes());

        dailySummary.setCurrentVoteDensity(dailySummaryDto.getCurrentVoteDensity());
        dailySummary.setTodayAvgRuntimeMinutes(dailySummaryDto.getTodayAvgRuntimeMinutes());

        dailySummary.setOverallAdultMoviesPerc(dailySummaryDto.getOverallAdultMoviesPerc());
        dailySummary.setTodayAdultMoviesPerc(dailySummaryDto.getTodayAdultMoviesPerc());

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


}
