package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.dtos.MovieRow;
import com.luca.imdb_movie_rating.dtos.RatingRow;
import com.luca.imdb_movie_rating.services.TsvReaderService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class TsvReaderServiceImpl implements TsvReaderService {

    private String ratingsTsvFilePath="C:\\Users\\Utente\\OneDrive\\Desktop\\csv_imdb\\current\\title.ratings.tsv";

    private String getTitleBasicsTsvSavePath="C:\\Users\\Utente\\OneDrive\\Desktop\\csv_imdb\\current\\title.basics.tsv";

    @Override
    public List<MovieRow> readTitlesTsv(String tsvPath, Map<String,RatingRow> ratingMap) {

        List<MovieRow> movieRows=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(new File(getTitleBasicsTsvSavePath)))){

            // skip headers
            br.readLine();

            while(br.ready()){
                String line=br.readLine();


                System.out.println(line);

                String[] arr=line.split("\t");

                if(arr[1].equals("movie")){

                    String tConst=arr[0];

                    RatingRow ratingRow=ratingMap.get(arr[0]);

                    if(ratingRow!=null){

                        boolean isAdult=Boolean.parseBoolean(arr[4]);

                        int year=Integer.parseInt(arr[5]);

                        Integer runtimeMinutes;
                        try{
                             runtimeMinutes=Integer.parseInt(arr[7]);
                        }catch(NumberFormatException e){
                            runtimeMinutes=null;
                        }


                        MovieRow movieRow=new MovieRow(tConst,arr[2],arr[3],isAdult,year,runtimeMinutes,arr[8],ratingRow);

                        movieRows.add(movieRow);

                    }

                }
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return movieRows;

    }

    @Override
    public  Map<String,RatingRow> readRatingsTsv(String tsvPath) {

        Map<String,RatingRow> ratingMap = new HashMap<>();

        try(BufferedReader br=new BufferedReader(new FileReader(new File(ratingsTsvFilePath)))){

            // skip headers
            br.readLine();

            while(br.ready()){
                String line=br.readLine();

                String[] arr=line.split("\t");

                int numVotes=Integer.parseInt(arr[2]);

                if(numVotes>=2000){

                    Float avgRating=Float.parseFloat(arr[1]);

                    RatingRow rating=new RatingRow(arr[0],Float.parseFloat(arr[1]),numVotes);

                    ratingMap.put(arr[0],rating);

                }
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ratingMap;

    }
}
