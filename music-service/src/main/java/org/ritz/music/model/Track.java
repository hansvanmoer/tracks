/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.model;

import java.io.Serializable;
import java.util.Objects;
import org.ritz.music.facet.Facet;

/**
 *
 * @author hans
 */
public class Track implements Serializable, Identifiable {
    
    public static Facet<Track> TITLE_FACET = new Facet<Track>(Track.class, "title");
    public static Facet<Track> ARTIST_FACET = new Facet<Track>(Track.class, "artist.name");
    public static Facet<Track> SCORE_FACET = new Facet<Track>(Track.class, "score");
    public static Facet<Track> RANK_FACET = new Facet<Track>(Track.class, "rank");
    
    private Long trackId;
    private String title;
    private Artist artist;
    private String keywords;
    private Integer score;
    private Integer rank;

    public Track(){
        this.score = 0;
    }
    
    public Track(String title, Artist artist){
        this.title = title;
        this.artist = artist;
        this.keywords = createKeyWords(title, artist);
        this.score = 0;
        this.rank = 0;
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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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
    
    public Object getUniqueId(){
        return trackId;
    }
    
}
