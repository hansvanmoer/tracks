/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import org.ritz.music.model.Track;
import org.ritz.music.model.User;
import org.ritz.music.service.TrackService;
import org.ritz.music.service.UserService;
import org.ritz.music.web.data.OutputHandle;

/**
 *
 * @author hans
 */
public class Administrator implements Serializable{
    
    private TrackService trackService;
    private FacetedDataModel<Track> tracks;
    private DataExporter<Track> trackExporter;
    private UserService userService;
    private FacetedDataModel<User> users;
    private DataExporter<User> userExporter;
    
    private DataFormatFactory dataFormatFactory;
    
    private static final String TRACK_FILE_NAME = "tracks";
    private static final String USER_FILE_NAME = "users";    
    private static final DateFormat EXPORT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy"); 
    
    public Administrator(){}
    
    @PostConstruct
    public void init(){
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
            Track.RANK_FACET.setHandle(OutputHandle.class, new OutputHandle<Track>(){
                public Serializable getValue(Track track){
                    return track.getRank();
                }
            });
        }
        if(!User.FIRST_NAME_FACET.hasHandle(OutputHandle.class)){
            User.FIRST_NAME_FACET.setHandle(OutputHandle.class, new OutputHandle<User>(){
                @Override
                public Serializable getValue(User model) {
                    return model.getFirstName();
                }
            });
            User.LAST_NAME_FACET.setHandle(OutputHandle.class, new OutputHandle<User>(){
                @Override
                public Serializable getValue(User model) {
                    return model.getLastName();
                }
            });
            User.EMAIL_ADDRESS_FACET.setHandle(OutputHandle.class, new OutputHandle<User>(){
                @Override
                public Serializable getValue(User model) {
                    return model.getEmailAddress();
                }
            });
            User.TELEPHONE_FACET.setHandle(OutputHandle.class, new OutputHandle<User>(){
                @Override
                public Serializable getValue(User model) {
                    return model.getTelephone();
                }
            });
            User.BIRTH_DATE_FACET.setHandle(OutputHandle.class, new OutputHandle<User>(){
                @Override
                public Serializable getValue(User model) {
                    return EXPORT_DATE_FORMAT.format(model.getBirthDate());
                }
            });
            User.ANSWER_FACET.setHandle(OutputHandle.class, new OutputHandle<User>(){
                @Override
                public Serializable getValue(User model) {
                    return model.getAnswer();
                }
            });
        }
        this.tracks = new FacetedDataModel<Track>(trackService, Track.TITLE_FACET, Track.ARTIST_FACET, Track.SCORE_FACET, Track.RANK_FACET);
        this.trackExporter = new DataExporter<Track>(this.tracks, TRACK_FILE_NAME, true, dataFormatFactory.getDefaultDataFormat());
        this.users = new FacetedDataModel<User>(userService, User.FIRST_NAME_FACET, User.LAST_NAME_FACET, User.EMAIL_ADDRESS_FACET, User.TELEPHONE_FACET, User.BIRTH_DATE_FACET, User.ANSWER_FACET);
        this.userExporter = new DataExporter<User>(this.users, USER_FILE_NAME, true, dataFormatFactory.getDefaultDataFormat());
    }

    public FacetedDataModel<Track> getTracks() {
        return tracks;
    }

    public TrackService getTrackService() {
        return trackService;
    }

    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    public DataFormatFactory getDataFormatFactory() {
        return dataFormatFactory;
    }

    public void setDataFormatFactory(DataFormatFactory dataFormatFactory) {
        this.dataFormatFactory = dataFormatFactory;
    }

    public DataExporter<Track> getTrackExporter() {
        return trackExporter;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public FacetedDataModel<User> getUsers() {
        return users;
    }

    public DataExporter<User> getUserExporter() {
        return userExporter;
    }
}
