package com.luca.imdb_movie_rating.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ReportUtils {

    private static final String POSITION="POSIZIONE";

    private static final String T_CONST="CODICE";

    private static final String PRIMARY_TITLE="TITOLO";

    private static final String ORIGINAL_TITLE="TITOLO ORIGINARIO";

    private static final String NEW_VOTES="NUOVI VOTI";

    private static final String NUM_TOTAL_VOTES="NUMERO VOTI TOTALE";

    private static final String AVG_RATING_DIFF="VARIAZIONE VOTO MEDIO";

    private static final String AVG_RATING="VOTO MEDIO";

    private static final String YEAR = "ANNO";

    private static final String RUNTIME="DURATA";

    private static final String IS_ADULT="PER ADULTI";

    private static final String GENRE="GENERE";

    private static final String NUM_MOVIES_ANALIZED="NUMERO FILM ANALIZZATI";

    private static final String NEW_MOVIES="NUOVI FILM";

    private static final String TOTAL_AVG_NUMBER_VOTES="NUMERO MEDIO VOTI";

    private static final String VOTES_DENSITY="NUOVI VOTI/NUMERO FILM";

    private static final String AVG_DURATION="DURATA MEDIA";

    private static final String NEW_MOVIES_AVG_DURATION="DURATA MEDIA NUOVI FILM";

    private static final String NUM_IS_ADULT="NUMERO FILM PER ADULTI";

    private static final String ADULT_MOVIES_PERCENTAGE="PERCENTUALE FILM PER ADULTI";

    private static final String START_DATE="DATA INIZIO";

    private static final String END_DATE="DATA FINE";


    private ReportUtils(){}


    public static String createTrendingMoviesHeader(){
        return String.join(",",POSITION,T_CONST,PRIMARY_TITLE,ORIGINAL_TITLE,NEW_VOTES,NUM_TOTAL_VOTES,AVG_RATING_DIFF,AVG_RATING,YEAR,RUNTIME,
                IS_ADULT,GENRE)+"\n";
    }

    public static String createDailySummaryHeader(){
        return String.join(",",GENRE,NUM_MOVIES_ANALIZED,NEW_MOVIES,NUM_TOTAL_VOTES,NEW_VOTES,TOTAL_AVG_NUMBER_VOTES,VOTES_DENSITY,AVG_RATING,AVG_RATING_DIFF
        ,AVG_DURATION,NEW_MOVIES_AVG_DURATION,NUM_IS_ADULT,ADULT_MOVIES_PERCENTAGE,START_DATE,END_DATE)+"\n";
    }

    public static String escapeString(String source){

        if(source==null){
            return "";
        }

        if(source.contains("\"")) {
            source=source.replace("\"","\"\"");
        }

        if(source.contains(",")){
            source="\""+source+"\"";
        }

        return source;
    }

    public static String formatDuration(Integer value){
        String result="NA";
        if(value!=null){
            int hours=value.intValue()/60;
            int minutes=value.intValue()%60;
            result= String.format("%02d:%02d", hours, minutes);
        }
        return result;
    }

    public static String parseBoolean(boolean value){
        return value?"Si":"No";
    }

    public static String formatPercentage(Double value){
        DecimalFormat df = new DecimalFormat("0.0000",new DecimalFormatSymbols(Locale.US));
        df.setRoundingMode(RoundingMode.HALF_UP);
        return value!=null?df.format(value)+" %":"";
    }

    public static String formatDouble(Double value){
        DecimalFormat df = new DecimalFormat("0.0000",new DecimalFormatSymbols(Locale.US));
        df.setRoundingMode(RoundingMode.HALF_UP);
        return value!=null?df.format(value):"";
    }
}
