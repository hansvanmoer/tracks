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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.ritz.music.model.QueryResult;
import org.ritz.music.service.MusicServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class HibernateService<Entity extends Serializable, Key extends Serializable> implements Serializable {
    
    private static Logger LOG = LoggerFactory.getLogger(HibernateService.class);
    
    private Class entityClass;
    private SessionFactory sessionFactory;
    
    protected HibernateService(Class entityClass){
        this.entityClass = entityClass;
    }
    
    
    protected Entity get(Key key) throws MusicServiceException{
        Session session = getSession();
        Transaction transaction = beginTransaction(session);
        try{
            return (Entity)session.get(entityClass,key);
        }catch(HibernateException e){
            rollback(transaction);
            throw new MusicServiceException(String.format("unable to get \"%s\" with primary key \"%s\"",entityClass.getName(), key.toString()), e);
        }finally{
            close(session, transaction);
        }
    }
    
    protected Entity saveOrUpdate(Entity entity) throws MusicServiceException{
        Session session = getSession();
        Transaction transaction = beginTransaction(session);
        try{
            session.saveOrUpdate(entity);
            return entity;
        }catch(HibernateException e){
            rollback(transaction);
            throw new MusicServiceException(String.format("unable tocreate or update \"%s\"",entityClass.getName()), e);
        }finally{
            close(session, transaction);
        }
    }
    
    protected List<Entity> getAll() throws MusicServiceException{
        Session session = getSession();
        Transaction transaction = beginTransaction(session);
        try{
            return (List<Entity>)session.createCriteria(entityClass).list();
        }catch(HibernateException e){
            rollback(transaction);
            throw new MusicServiceException(String.format("unable tocreate or update \"%s\"",entityClass.getName()), e);
        }finally{
            close(session, transaction);
        }
    }
    
    protected List<Entity> getList(CriteriaPreparator criteriaPreparator) throws MusicServiceException{
        Session session = getSession();
        Transaction transaction = beginTransaction(session);
        try{
            return (List<Entity>)criteriaPreparator.prepare(session.createCriteria(entityClass)).list();
        }catch(HibernateException e){
            rollback(transaction);
            throw new MusicServiceException(String.format("unable tocreate or update \"%s\"",entityClass.getName()), e);
        }finally{
            close(session, transaction);
        }
    }
    
    protected QueryResult<Entity> getList(CriteriaPreparator criteriaPreparator, int firstResultIndex, int maxResultSize) throws MusicServiceException{
        Session session = getSession();
        Transaction transaction = beginTransaction(session);
        try{
            Criteria criteria = criteriaPreparator.prepare(session.createCriteria(entityClass))
                    .setMaxResults(maxResultSize)
                    .setFirstResult(firstResultIndex);
            return new QueryResult<Entity>((List<Entity>)criteria.list(),
                    ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());  //ninja API change fro Hibernat
        }catch(HibernateException e){
            rollback(transaction);
            throw new MusicServiceException(String.format("unable tocreate or update \"%s\"",entityClass.getName()), e);
        }finally{
            close(session, transaction);
        }
    }
    
    
    protected Entity getUniqueResult(CriteriaPreparator criteriaPreparator) throws MusicServiceException{
        Session session = getSession();
        Transaction transaction = beginTransaction(session);
        try{
            return (Entity)criteriaPreparator.prepare(session.createCriteria(entityClass)).uniqueResult();
        }catch(HibernateException e){
            throw new MusicServiceException(String.format("unable tocreate or update \"%s\"",entityClass.getName()), e);
        }finally{
            close(session, transaction);
        }
    }
    
    protected Session getSession(){
        return sessionFactory.openSession();
    }
    
    protected Transaction beginTransaction(Session session) throws MusicServiceException{
        try{
            return  session.beginTransaction();
        }catch(HibernateException e){
            throw new MusicServiceException("unable to begin transaction", e);
        }
    }
    
    protected void rollback(Transaction transaction) throws MusicServiceException{
        try{
            transaction.rollback();
        }catch(HibernateException e){
            LOG.error("unable to rollback transaction", e);
        }
    }
    
    protected void close(Session session, Transaction transaction) throws MusicServiceException{
        if(session != null){
            try{
                if(transaction != null && transaction.isActive()){
                    transaction.commit();
                }    
            }catch(HibernateException e){
                LOG.error("unable to commit transaction", e);
                try{
                    transaction.rollback();
                }catch(HibernateException e2){
                    LOG.error("unable to rollback transaction", e2);
                }
            }finally{
                try{
                    session.close();
                }catch(HibernateException e){
                    LOG.error("unable to close session", e);
                }
            }
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    protected static class AllResults implements CriteriaPreparator{
    
        public Criteria prepare(Criteria criteria){
            return criteria;
        }
        
    }
    
    protected static final CriteriaPreparator ALL_RESULTS = new AllResults();
}
