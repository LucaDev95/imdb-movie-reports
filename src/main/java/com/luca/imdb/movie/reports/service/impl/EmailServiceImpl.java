package com.luca.imdb.movie.reports.service.impl;

import com.luca.imdb.movie.reports.config.ExecutionProperties;
import com.luca.imdb.movie.reports.exception.ApplicationException;
import com.luca.imdb.movie.reports.exception.ReportException;
import com.luca.imdb.movie.reports.service.EmailService;
import com.luca.imdb.movie.reports.util.FormatUtils;
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

    private final ExecutionProperties executionProperties;

    public EmailServiceImpl(JavaMailSender javaMailSender, ExecutionProperties executionProperties){
        this.javaMailSender=javaMailSender;
        this.executionProperties=executionProperties;
    }

    @Override
    public void sendSummaryMail(String trendingMoviesReport,String summaryReport) {

        LocalDate today= LocalDate.now();

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(executionProperties.getRecipient());
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

            helper.setTo(executionProperties.getRecipient());
            helper.setSubject("Application Error");
            helper.setText(applicationException.getMailText(), true);

            javaMailSender.send(message);

        }catch(MessagingException e){

            logger.error("cannot send mail. Error is",e);

            logger.error("Application error: ",applicationException);


        }

    }

}
