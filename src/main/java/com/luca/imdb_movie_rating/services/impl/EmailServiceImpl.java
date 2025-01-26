package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    @Override
    public void sendMail() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo("luca.dona95mb@gmail.com");
        helper.setSubject("csv imdb");
        helper.setText("testo del messaggio", true); // true per abilitare HTML nel corpo del

        File provaTxt=new File("C:\\Users\\Utente\\OneDrive\\Desktop\\csv_imdb\\prova_invio.txt");

        helper.addAttachment(provaTxt.getName(), provaTxt);

        javaMailSender.send(message);
    }
}
