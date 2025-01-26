package com.luca.imdb_movie_rating.services.impl;

import com.luca.imdb_movie_rating.services.DownloadTsvService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;

@Service
public class DownloadTsvServiceImpl implements DownloadTsvService {

    private String ratingsUrl="https://datasets.imdbws.com/title.ratings.tsv.gz";

    private String titleBasicsTsv="https://datasets.imdbws.com/title.basics.tsv.gz";

    private String ratingsTsvFilePath="E:\\csv\\current\\title.ratings.tsv";

    private String getTitleBasicsTsvSavePath="E:\\csv\\current\\title.basics.tsv";

    private String compressExtension=".gz";


    @Override
    public void downloadTsv() {

        // see all tsv at https://datasets.imdbws.com/
       /* try {
            downloadTsv(ratingsUrl,ratingsTsvFilePath);
            System.out.println("File scaricato con successo: " + ratingsTsvFilePath);
            downloadTsv(titleBasicsTsv,getTitleBasicsTsvSavePath);
            System.out.println("File scaricato con successo: " + getTitleBasicsTsvSavePath);
        } catch (IOException e) {
            System.out.println("Errore durante il download: " + e.getMessage());
        }*/


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
}
