/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web.data;

import java.io.Serializable;
import org.ritz.music.facet.Facet;
import org.ritz.music.facet.FacetException;
import org.ritz.music.facet.FacetModel;
import org.ritz.music.model.Track;

/**
 *
 * @author hans
 */
public class TrackDataModel{
    
    public TrackDataModel(){
        if(!Track.TITLE_FACET.hasHandle(OutputHandle.class)){
            Track.TITLE_FACET.setHandle(OutputHandle.class, new OutputHandle<Track>() {
                public Serializable getValue(Track track){
                    return track.getTitle();
                }
            });
            Track.ARTIST_FACET.setHandle(OutputHandle.class, new OutputHandle<Track>(){
                public Serializable getValue(Track track){
                    return track.getArtist().getName();
                }
            });
            Track.SCORE_FACET.setHandle(OutputHandle.class, new OutputHandle<Track>(){
                public Serializable getValue(Track track){
                    return track.getScore();
                }
            });
        }
    }
    
}
