/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.ritz.music.model.Track;
import org.ritz.music.service.UserService;
import org.ritz.music.service.VoteService;

/**
 *
 * @author hans
 */
public class Voter implements Serializable{
    
    
    private UserService userService;
    private VoteService voteService;
    
    private String emailAddress;
    private String firstName;
    private String lastName;
    private List<Track> selectedTracks;
    
    public Voter(){}

    public void add(Track track){
        if(!selectedTracks.contains(track)){
            selectedTracks.add(track);
        }
    }
    
    public void remove(Track track){
        selectedTracks.remove(track);
    }
    
    public List<Track> getSelectedTracks() {
        return selectedTracks;
    }

    public void setSelectedTracks(List<Track> selectedTracks) {
        this.selectedTracks = selectedTracks;
    }
    
    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public VoteService getVoteService() {
        return voteService;
    }

    public void setVoteService(VoteService voteService) {
        this.voteService = voteService;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
