package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.dtos.RatingResult;
import com.luca.imdb_movie_rating.repositories.MovieRepository;
import com.luca.imdb_movie_rating.services.RatingService;
import org.springframework.stereotype.Service;
import com.luca.imdb_movie_rating.services.ReportService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {


    private final RatingService ratingService;

    public ReportServiceImpl(RatingService ratingService){
        this.ratingService=ratingService;
    }


    @Override
    public void generateDailyReport() {

        List<RatingResult> ratingResultList= ratingService.loadRatingResult();

        writeCsv(ratingResultList);

    }

    private void writeCsv(List<RatingResult> ratingResultList){

        try(BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\Utente\\OneDrive\\Desktop\\csv_imdb\\tmp\\ratings.csv"))){

            int rowNum=1;
            String header="rowNum,tConst,primaryTitle,origTitle,numVotesDiff,numVotes,avgRatingDiff,currentAvgRating,year,runtimeMinutes,isAdult,genres\n";

            bw.write(header);
            for(RatingResult r : ratingResultList){
                String num=rowNum+"";
                String tConst=r.gettConst();
                String primaryTitle=escapeString(r.getPrimaryTitle());
                String origTitle=escapeString(r.getOrigTitle());
                String runtimeMinutes=r.getRuntimeMinutes()!=null?r.getRuntimeMinutes()+"":"NA";
                String genres=escapeString(r.getGenres());

                String isAdult=parseBoolean(r.getAdult());

                String avgRatingDiff=r.getAvgRatingDiff()+"";

                String currAverageRating= r.getCurrentAvgRating()+"";

                String numVotesDiff=r.getNumVotesDiff()+"";

                String year=r.getYear()+"";

                String numVotes=r.getCurrentNumVotes()+"";

                String line=num+","+tConst+","+primaryTitle+","+origTitle+","+numVotesDiff+","+numVotes
                        +","+avgRatingDiff+","+currAverageRating+","+year+","+runtimeMinutes+","+isAdult+","+genres+"\n";

                bw.write(line);

                rowNum++;

            }


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private String escapeString(String source){

        if(source.contains("\"")) {
            source=source.replace("\"","\"\"");
        }

        if(source.contains(",")){
            source="\""+source+"\"";
        }

        return source;
    }

    private String parseBoolean(boolean value){
        return value?"Y":"N";
    }
}
