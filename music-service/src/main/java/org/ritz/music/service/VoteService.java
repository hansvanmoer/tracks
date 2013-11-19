/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service;

import java.util.List;
import org.ritz.music.model.Vote;


/**
 *
 * @author hans
 */
public interface VoteService {
    
    Vote getVote(Long userId, Long trackId) throws MusicServiceException;
    
    List<Vote> getVotes(Long userId) throws MusicServiceException;
    
    Integer getScore(Long trackId) throws MusicServiceException;
    
    void addVote(Vote vote) throws MusicServiceException;
    
    void addVotes(List<Vote> votes) throws MusicServiceException;
    
}
