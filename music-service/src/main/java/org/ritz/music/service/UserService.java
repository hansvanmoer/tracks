/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service;

import java.util.List;
import org.ritz.music.model.User;
import org.ritz.music.model.Vote;

/**
 * @author hans
 */
public interface UserService extends FacetedSearch<User>{
    
    void addUser(User user) throws MusicServiceException;
    
    void addUser(User user, List<Vote> votes) throws MusicServiceException;
    
    List<User> getUsers() throws MusicServiceException;
    
    User getUser(Long userId) throws MusicServiceException;
    
}
