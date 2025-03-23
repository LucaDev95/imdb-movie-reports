package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.config.SummaryProperties;
import com.luca.imdb_movie_rating.exceptions.ApplicationException;
import com.luca.imdb_movie_rating.exceptions.ReportException;
import com.luca.imdb_movie_rating.services.EmailService;
import com.luca.imdb_movie_rating.utils.FormatUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;

import java.time.LocalDate;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender javaMailSender;

    private final SummaryProperties summaryProperties;

    public EmailServiceImpl(JavaMailSender javaMailSender, SummaryProperties summaryProperties){
        this.javaMailSender=javaMailSender;
        this.summaryProperties=summaryProperties;
    }

    @Override
    public void sendSummaryMail(String trendingMoviesReport,String summaryReport) {

        LocalDate today= LocalDate.now();

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(summaryProperties.getRecipient());
            helper.setSubject("csv imdb "+FormatUtils.formatDate(today));
            helper.setText("In allegato i csv relativi al giorno "+FormatUtils.formatDate(today), true);

            ByteArrayResource trendingMoviesReportAttachment = new ByteArrayResource(trendingMoviesReport.getBytes(StandardCharsets.UTF_8));
            ByteArrayResource summaryReportAttachment = new ByteArrayResource(summaryReport.getBytes(StandardCharsets.UTF_8));

            String trendingMoviesFileName="trendingMovies_"+ FormatUtils.formatCsvDate(today)+".csv";
            String summaryFileName="dailySummary_"+ FormatUtils.formatCsvDate(today)+".csv";

            helper.addAttachment(trendingMoviesFileName, trendingMoviesReportAttachment,"text/csv");
            helper.addAttachment(summaryFileName,summaryReportAttachment,"text/csv");

            javaMailSender.send(message);
        }catch(MessagingException e){

            throw new ReportException("Error while sending report mail",e);

        }
    }

    @Override
    public void sendErrorMail(ApplicationException applicationException){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {

            helper.setTo(summaryProperties.getRecipient());
            helper.setSubject("Application Error");
            helper.setText(applicationException.getMailText(), true);

            javaMailSender.send(message);

        }catch(MessagingException e){

            logger.error("cannot send mail. Error is",e);

            logger.error("Application error: ",applicationException);


        }

    }

}
