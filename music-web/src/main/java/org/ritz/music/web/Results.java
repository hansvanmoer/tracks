/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.ritz.music.model.Track;
import org.ritz.music.model.User;
import org.ritz.music.service.TrackService;
import org.ritz.music.service.UserService;

/**
 *
 * @author hans
 */
public class Results implements Serializable{
    
    private TrackService trackService;
    private FacetedDataModel<Track> tracks;
    private UserService userService;
    private FacetedDataModel<User> users;

    public Results(){}
    
    @PostConstruct
    public void init(){
        this.tracks = new FacetedDataModel<Track>(trackService, Track.TITLE_FACET, Track.ARTIST_FACET, Track.RANK_FACET);
        this.users = new FacetedDataModel<User>(userService, User.FIRST_NAME_FACET, User.LAST_NAME_FACET, User.SCORE_FACET);
    }
    
    public FacetedDataModel<Track> getTracks() {
        return tracks;
    }

    public FacetedDataModel<User> getUsers() {
        return users;
    }
    
    public TrackService getTrackService() {
        return trackService;
    }

    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
}
