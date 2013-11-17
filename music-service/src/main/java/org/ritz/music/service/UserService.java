/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service;

import java.util.List;
import org.ritz.music.model.User;

/**
 * @author hans
 */
public interface UserService {
    
    void addUser(User user) throws MusicServiceException;
    
    List<User> getUsers() throws MusicServiceException;
    
    User getUser(Long userId) throws MusicServiceException;
    
}
