/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.ritz.music.model.User;
import org.ritz.music.model.UserState;
import org.ritz.music.model.Vote;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.UserService;
import org.ritz.music.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class Voter implements Serializable{

    private static Logger LOG = LoggerFactory.getLogger(Voter.class);
    
    private User user;
    private Integer answer;
    
    private UserService userService;
    private VoteService voteService;
    private TrackList trackList;
    
    public Voter(){
        this.user = new User("","","","",new Date(), 0);
        this.answer = 0;
    }
        
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
    
    public String vote(){
        if(trackList.isSelectionComplete()){
            try{
                List<Vote> votes = new ArrayList<Vote>(ApplicationConstants.SELECTED_TRACKS_COUNT);
                for(int i = 0;i<ApplicationConstants.SELECTED_TRACKS_COUNT; i++){
                    votes.add(new Vote(user.getUserId(), trackList.getSelectedTracks().get(i).getTrackId(), ApplicationConstants.SELECTED_TRACKS_SCORES[i]));
                }
                userService.addUser(user, votes);
                return "confirm";
            }catch(MusicServiceException e){
                LOG.info(String.format("failed to add user: '%s'", user.getEmailAddress()), e);
                FacesContext.getCurrentInstance().addMessage("vote-failed", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Already voted","A user with this email address has already voted"));
            }
        }else{
            FacesContext.getCurrentInstance().addMessage("vote-failed", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incomplete vote","You should select more tracks"));
        }
        return "vote";
    }
    
    public void reset(){
        this.user = new User("","","","",new Date(), 0);
        this.answer = 0;
    }
    
    public String logout(){
        reset();
        this.trackList.reset();
        return "tracks";
    }

    public TrackList getTrackList() {
        return trackList;
    }

    public void setTrackList(TrackList trackList) {
        this.trackList = trackList;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    public boolean hasVoted(){
        return this.user.getState() == UserState.VOTED;
    }
    
}