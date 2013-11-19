/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service.hibernate;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.ritz.music.model.User;
import org.ritz.music.model.UserState;
import org.ritz.music.model.Vote;
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
    public void addUser(User user, List<Vote> votes) throws MusicServiceException {
        Session session = getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            user.setState(UserState.VOTED);
            session.saveOrUpdate(user);
            for(Vote vote : votes){
                vote.setUserId(user.getUserId());
                session.saveOrUpdate(vote);
            }
        }catch(HibernateException e){
            rollback(transaction);
            throw new MusicServiceException(String.format("unable to create user '%s' and cast votes",user.getEmailAddress()), e);
        }finally{
            close(session, transaction);
        }
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
