/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.DragDropEvent;
import org.ritz.music.model.Track;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class TrackList implements Serializable{
    
    private static final Logger LOG = LoggerFactory.getLogger(TrackList.class);
    
    private static final int MAX_SELECTED_TRACKS = 5;
    private static final int[] SCORES = {5,4,3,2,1}; 
    
    private TrackService trackService;
    private String searchQuery;
    
    private List<Track> tracks;
    private List<Track> selectedTracks;
    
    public TrackList(){
        this.selectedTracks = new ArrayList<Track>(MAX_SELECTED_TRACKS);
        this.searchQuery = "";
    }

    public TrackService getTrackService() {
        return trackService;
    }

    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getSelectedTracks() {
        return selectedTracks;
    }

    public void setSelectedTracks(List<Track> selectedTracks) {
        this.selectedTracks = selectedTracks;
    }
    
    @PostConstruct
    public void loadTracks(){
        try{
            this.tracks = trackService.getTracks();
        }catch(MusicServiceException e){
            LOG.error("unable to fetch tracks", e);
        }
    }
    
    public void select(Track track){
        if(this.selectedTracks.size() < MAX_SELECTED_TRACKS && !this.selectedTracks.contains(track)){
            this.selectedTracks.add(track);
            this.update();
        }
    }
    
    public void deselect(Track track){
        if(this.selectedTracks.contains(track)){
            this.selectedTracks.remove(track);
        }
    }
    
    public void queryChanged(){
        update();
    }
    
    public void trackDropped(AjaxBehaviorEvent event) {  
        Track track = ((Track) ((DragDropEvent)event).getData());  
        select(track);
    }
    
    public void update(){
        try{
            this.tracks = trackService.getTracks(parseSearchTerms(searchQuery));
            this.tracks.removeAll(this.selectedTracks);
        }catch(MusicServiceException e){
            LOG.error("unable to update track list", e);
        }
    }
    
    private List<String> parseSearchTerms(String query){
        String[] terms = query.split("\\W");
        List<String> result = new ArrayList<String>(terms.length);
        for(String term : terms){
            if(!term.isEmpty()){
                result.add(term);
            }
        }
        return result;
    }
    
}
