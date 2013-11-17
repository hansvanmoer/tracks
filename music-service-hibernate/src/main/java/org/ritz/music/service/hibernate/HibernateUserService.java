/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service.hibernate;

import java.io.Serializable;
import java.util.List;
import org.ritz.music.model.User;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.UserService;

/**
 *
 * @author hans
 */
public class HibernateUserService extends HibernateService<User, Long> implements UserService, Serializable{

    public HibernateUserService(){
        super(User.class);
    }
    
    @Override
    public void addUser(User user) throws MusicServiceException {
        super.saveOrUpdate(user);
    }

    @Override
    public List<User> getUsers() throws MusicServiceException {
        return super.getAll();
    }

    @Override
    public User getUser(Long userId) throws MusicServiceException {
        return super.get(userId);
    }
    
}
