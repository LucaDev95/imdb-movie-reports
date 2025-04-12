package com.luca.imdb.movie.reports.service.impl;

import com.luca.imdb.movie.reports.config.ExecutionProperties;
import com.luca.imdb.movie.reports.dto.RatingRow;
import com.luca.imdb.movie.reports.entitiy.Movie;
import com.luca.imdb.movie.reports.repository.MovieRepository;
import com.luca.imdb.movie.reports.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTest {

    @InjectMocks
    private RatingServiceImpl classUnderTest;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ExecutionProperties executionProperties;

    @Test
    public void saveRatingRowsTest(){

        float rating=6.7f;
        long movieId=1l;

        RatingRow rating1=new RatingRow(1l,rating,500);

        Movie movie =new Movie();
        movie.setId(rating1.movieId());

        List<RatingRow> ratingRowList=new ArrayList<>(Arrays.asList(rating1));
        when(movieRepository.getReferenceById(movieId)).thenReturn(movie);

        classUnderTest.saveRatingRows(ratingRowList);
        verify(ratingRepository).saveAll(argThat(list->list.iterator().next().getAverageRating().equals(rating)));
        verify(ratingRepository).saveAll(argThat(list->list.iterator().next().getMovie().getId().equals(movieId)));

    }

    @Test
    public void deleteCurrentRatingsTest(){
        LocalDate currentDate = LocalDate.of(2025, 04, 12);
        when(executionProperties.getCurrentDate()).thenReturn(currentDate);
        classUnderTest.deleteCurrentRatings();
        verify(ratingRepository).deleteCurrentRatings(eq(currentDate));
    }

    @Test
    public void checkReportGenerationTest() {

        LocalDate currentDate = LocalDate.of(2025, 04, 12);

        Integer summaryIntervalDays = 10;

        LocalDate intervalDate = LocalDate.of(2025, 04, 2);

        when(executionProperties.getCurrentDate()).thenReturn(currentDate);
        when(executionProperties.getSummaryIntervalDays()).thenReturn(summaryIntervalDays);

        classUnderTest.checkReportGeneration();

        verify(ratingRepository).countRatingsInReportInterval(eq(intervalDate));
    }

}
