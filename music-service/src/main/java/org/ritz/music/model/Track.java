/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author hans
 */
public class Track implements Serializable {
    private Long trackId;
    private String title;
    private Artist artist;
    private String keywords;
    private Integer score;

    public Track(){}
    
    public Track(String title, Artist artist){
        this.title = title;
        this.artist = artist;
        this.keywords = createKeyWords(title, artist);
        this.score = null;
    }
    
    private static String createKeyWords(String title, Artist artist){
        return (title + (artist != null ? " "+artist.getName() : "")).toLowerCase().replaceAll("\\W", " ");
    }
    
    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.keywords = createKeyWords(title, artist);
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        this.keywords = createKeyWords(title, artist);
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.trackId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Track other = (Track) obj;
        if (!Objects.equals(this.trackId, other.trackId)) {
            return false;
        }
        return true;
    }
    
}
