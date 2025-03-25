package com.luca.imdb_movie_rating.service.impl;

import com.luca.imdb_movie_rating.config.ImdbProperties;
import com.luca.imdb_movie_rating.service.DownloadTsvService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

@Service
public class DownloadTsvServiceImpl implements DownloadTsvService {

    private String ratingsUrl="https://datasets.imdbws.com/title.ratings.tsv.gz";

    private String titleBasicsTsv="https://datasets.imdbws.com/title.basics.tsv.gz";

    private String ratingsTsvFilePath="C:\\Users\\Utente\\OneDrive\\Desktop\\csv_imdb\\current\\title.ratings.tsv";

    private String getTitleBasicsTsvSavePath="C:\\Users\\Utente\\OneDrive\\Desktop\\csv_imdb\\current\\title.basics.tsv";

    private String compressExtension=".gz";

    private final ImdbProperties imdbProperties;

    public DownloadTsvServiceImpl(ImdbProperties imdbProperties){
        this.imdbProperties=imdbProperties;
    }


    @Override
    public void downloadTsv() {

            deletePreviousFiles();

            CompletableFuture<Void> ratingsDownload = downloadTsv(ratingsUrl, ratingsTsvFilePath).exceptionally(ex->{
                System.out.println("Si è verificato un errore: " + ex.getMessage());
                return null;
            });
            CompletableFuture<Void> tilteBasicsDownload = downloadTsv(titleBasicsTsv, getTitleBasicsTsvSavePath).exceptionally(ex->{
                System.out.println("Si è verificato un errore: " + ex.getMessage());
                return null;
            });

            CompletableFuture.allOf(ratingsDownload,tilteBasicsDownload).join();



    }

    @Override
    public void testReadZip() {
        String urlString = imdbProperties.getBaseUrl()+imdbProperties.getBasicsGz();
        System.out.println("basic path: "+urlString);

        try( InputStream inputStream = URI.create(urlString).toURL().openStream()){

            try (GZIPInputStream gzipStream = new GZIPInputStream(inputStream);
                 InputStreamReader reader = new InputStreamReader(gzipStream);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {


                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public CompletableFuture<Void> downloadTsv(String gzUrl, String filePath) {

        return CompletableFuture.supplyAsync(()->{

            System.out.println("avviato downloadTsv "+gzUrl);

            try(ReadableByteChannel readableByteChannel = Channels.newChannel(URI.create(gzUrl).toURL().openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(filePath+compressExtension);){

                FileChannel fileChannel = fileOutputStream.getChannel();

                fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            }catch (IOException e){
                throw new RuntimeException("Errore nel downlaod da gzUrl",e);
            }


            GZIPInputStream gis = null;
            FileOutputStream fos = null;
            try{
                FileInputStream fis = new FileInputStream(filePath+compressExtension);
                gis = new GZIPInputStream(fis);
                fos = new FileOutputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while((len = gis.read(buffer)) != -1){
                    fos.write(buffer, 0, len);
                }
                fos.close();
                gis.close();


                Files.delete(Paths.get(filePath+compressExtension));


            }catch(IOException e){

                throw new RuntimeException("Errore nel salvataggio del file "+filePath,e);
            }

            finally{
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(gis!=null){
                    try {
                        gis.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println("completato downloadTsv "+gzUrl);
            return null;
        });




    }

    private void deletePreviousFiles(){
        Path directoryPath = Paths.get("C:\\Users\\Utente\\OneDrive\\Desktop\\csv_imdb\\current\\");

        try (Stream<Path> paths = Files.walk(directoryPath)) {
            paths.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.err.println("Errore durante l'elaborazione dei file: " + e.getMessage());
        }
    }
}
