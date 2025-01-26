package com.luca.imdb_movie_rating.services;

import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendMail()throws MessagingException;
}
