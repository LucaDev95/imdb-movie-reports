package com.luca.imdb_movie_rating.controllers;


import com.luca.imdb_movie_rating.services.EmailService;
import com.luca.imdb_movie_rating.services.LoadDailyService;
import com.luca.imdb_movie_rating.services.ResetDataService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("schedule")
public class SchedulingController {

    private final ResetDataService resetDataService;

    private final LoadDailyService loadDailyService;

    private final EmailService emailService;

    public SchedulingController(ResetDataService resetDataService,LoadDailyService loadDailyService,EmailService emailService) {
        this.resetDataService = resetDataService;
        this.loadDailyService=loadDailyService;
        this.emailService=emailService;
    }

    @GetMapping("resetData")
    public void resetData(){
        resetDataService.resetData();

    }

    @GetMapping("loadDaily")
    public void loadDaily(){
        loadDailyService.loadDaily();

    }


    @GetMapping("testMail")
    public void testMail() throws MessagingException {
        emailService.sendMail();

    }

}
