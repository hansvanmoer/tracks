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
import org.ritz.music.model.Track;
import org.ritz.music.model.User;
import org.ritz.music.model.UserState;
import org.ritz.music.model.Vote;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.UserService;
import org.ritz.music.service.hibernate.facet.BasicSearchHandle;
import org.ritz.music.service.hibernate.facet.FilterOperator;
import org.ritz.music.service.hibernate.facet.ManyToOneSearchHandle;
import org.ritz.music.service.hibernate.facet.SearchHandle;

/**
 *
 * @author hans
 */
public class HibernateUserService extends HibernateService<User, Long> implements UserService, Serializable{

    public HibernateUserService(){
        super(User.class);
        if(!User.FIRST_NAME_FACET.hasHandle(SearchHandle.class)){
            User.FIRST_NAME_FACET.setHandle(SearchHandle.class, new BasicSearchHandle("firstName", FilterOperator.LIKE));
            User.LAST_NAME_FACET.setHandle(SearchHandle.class, new BasicSearchHandle("lastName", FilterOperator.LIKE));
            User.EMAIL_ADDRESS_FACET.setHandle(SearchHandle.class, new BasicSearchHandle("emailAddress", FilterOperator.LIKE));
            User.TELEPHONE_FACET.setHandle(SearchHandle.class, new BasicSearchHandle("telephone", FilterOperator.LIKE));
            User.BIRTH_DATE_FACET.setHandle(SearchHandle.class, new BasicSearchHandle("birthDate", FilterOperator.EQUALS));
        }
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
                Track track = (Track)session.get(Track.class, vote.getTrackId());
                track.setScore(track.getScore()+vote.getScore());
                session.saveOrUpdate(track);
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
