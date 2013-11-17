/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web.config;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.ritz.music.model.Artist;
import org.ritz.music.model.Track;
import org.ritz.music.service.ArtistService;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class DataImporter implements Serializable{
    
    private static final String DATA_PATH = "/WEB-INF/tracks.csv";
    
    private static final Logger LOG = LoggerFactory.getLogger(DataImporter.class);
    
    private TrackService trackService;
    private ArtistService artistService;
    
    public DataImporter(){}

    @PostConstruct
    public void checkData(){
        try{
            if(trackService.getTracks(0, 1).getTotalResultCount() == 0){
                LOG.info("No tracks detected in datasource: data import will be started");
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ServletContext servletContext =  (ServletContext) facesContext.getExternalContext().getContext();
                InputStream input = servletContext.getResourceAsStream(DATA_PATH);
                if(input != null ){
                    try{
                        importData(input);
                    }catch(IOException e){
                        LOG.error("unable to import data", e);
                    }finally{
                        try{
                            input.close();
                        }catch(Exception e){}
                    }
                }else{
                    LOG.warn(String.format("No data file '%s' was detected: import cancelled", DATA_PATH));
                }
            }else{
                LOG.info("At least one track was detected: no data import needed");
            }
        }catch(MusicServiceException e){
            LOG.error("unable to access tracks : data import cancelled", e);
        }
    }
    
    private void addTrack(String title, String artistName){
        try{
            Artist artist = artistService.getArtist(artistName);
            if(artist == null){
                artist = artistService.addArtist(new Artist(artistName));
            }
            Track track = new Track(title,artist);
            trackService.addTrack(track);
        }catch(MusicServiceException e){
            LOG.error(String.format("unable to add track: '%s' with artist '%s'",title, artistName), e);
        }
    }
    
    private void importData(InputStream input) throws IOException{
        CSVReader reader = new CSVReader(new InputStreamReader(input));
        //assuming header line
        if(reader.readNext() == null){
            throw new IOException("empty file");
        }
        String[] line;
        try{
            while((line = reader.readNext()) != null){
                addTrack(line[0], line[1]);
            }
        }catch(IndexOutOfBoundsException e){
            throw new IOException("invalid file syntax: should be title, artist");
        }
    }
    
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }

}
