package com.luca.imdb.movie.reports.runner;


import com.luca.imdb.movie.reports.service.MainService;
import com.luca.imdb.movie.reports.service.MovieService;
import com.luca.imdb.movie.reports.service.ResetDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobRunnerTest {

    @InjectMocks
    private JobRunner classUnderTest;

    @Mock
    private ResetDataService resetDataService;

    @Mock
    private MainService loadDailyService;

    @Mock
    private MovieService movieService;


    @Test
    public void runFirstTimeTest() throws Exception {

        when(movieService.countMovies()).thenReturn(0l);

        classUnderTest.run(new String[]{});

        verify(resetDataService,times(1)).resetData();

        verify(loadDailyService,times(0)).loadDaily();



    }

    @Test
    public void runTest() throws Exception {

        when(movieService.countMovies()).thenReturn(50l);

        classUnderTest.run(new String[]{});

        verify(resetDataService,times(0)).resetData();

        verify(loadDailyService,times(1)).loadDaily();

    }


}
