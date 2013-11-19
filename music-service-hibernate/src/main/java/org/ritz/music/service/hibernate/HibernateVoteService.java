/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service.hibernate;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.ritz.music.model.Vote;
import org.ritz.music.model.VoteKey;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.VoteService;

/**
 *
 * @author hans
 */
public class HibernateVoteService extends HibernateService<Vote, VoteKey> implements VoteService, Serializable{

    public HibernateVoteService() {
        super(Vote.class);
    }

    @Override
    public Vote getVote(Long userId, Long trackId) throws MusicServiceException {
        return super.get(new VoteKey(userId, trackId));
    }

    @Override
    public List<Vote> getVotes(Long userId) throws MusicServiceException {
        return super.getList(new GetByUser(userId));
    }

    //TODO
    @Override
    public Integer getScore(Long trackId) throws MusicServiceException {
        Session session = getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            
            return (Integer)session.createCriteria(Vote.class)
                    .add(Restrictions.eq("trackId", trackId))
                    .setProjection(Projections.sum("score")).uniqueResult();
        }catch(HibernateException e){
            rollback(transaction);
            throw new MusicServiceException(String.format("unable tocreate or update \"%s\"",Vote.class.getName()), e);
        }finally{
            close(session, transaction);
        }
    }

    @Override
    public void addVote(Vote vote) throws MusicServiceException {
        Session session = getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            
            Vote existing = (Vote)session.createCriteria(Vote.class)
                    .add(Restrictions.eq("userId", vote.getUserId()))
                    .add(Restrictions.eq("score", vote.getScore())).uniqueResult();
            if(existing == null){
                session.saveOrUpdate(vote);
            }else{
                rollback(transaction);
                throw new MusicServiceException("unable to create vote: duplicate score");
            }
        }catch(HibernateException e){
            rollback(transaction);
            throw new MusicServiceException(String.format("unable tocreate or update \"%s\"",Vote.class.getName()), e);
        }finally{
            close(session, transaction);
        }
    }

    @Override
    public void addVotes(List<Vote> votes) throws MusicServiceException {
        Session session = getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            for(Vote vote : votes){
                Vote existing = (Vote)session.createCriteria(Vote.class)
                        .add(Restrictions.eq("userId", vote.getUserId()))
                        .add(Restrictions.eq("score", vote.getScore())).uniqueResult();
                if(existing == null){
                    session.saveOrUpdate(vote);
                }else{
                    rollback(transaction);
                    throw new MusicServiceException("unable to create vote: duplicate score");
                }
            }
        }catch(HibernateException e){
            rollback(transaction);
            throw new MusicServiceException(String.format("unable tocreate or update \"%s\"",Vote.class.getName()), e);
        }finally{
            close(session, transaction);
        }
    }
    
    
    
    private static class GetByUser implements CriteriaPreparator{
        
        private Long userId;
        
        public GetByUser(Long userId){
            this.userId = userId;
        }

        @Override
        public Criteria prepare(Criteria criteria) {
            return criteria.add(Restrictions.eq("userId", userId));
        }
    }
    
    private static class GetScore implements CriteriaPreparator{
        
        private Long trackId;
        
        public GetScore(Long trackId){
            this.trackId = trackId;
        }
        
        public Criteria prepare(Criteria criteria){
            return criteria.add(Restrictions.eq("trackId", trackId)).setProjection(Projections.sum("score"));
        }
    }
    
}
