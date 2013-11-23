/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.ritz.music.facet.Facet;
import org.ritz.music.model.QueryResult;
import org.ritz.music.model.Track;
import org.ritz.music.service.FacetedSearch;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.SortOrder;
import org.ritz.music.service.hibernate.facet.HibernateFacet;
import org.ritz.music.service.hibernate.facet.SearchHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class HibernateService<Entity extends Serializable, Key extends Serializable> implements FacetedSearch<Entity>, Serializable {
    
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
            Criteria countCriteria = criteriaPreparator.prepare(session.createCriteria(entityClass))
                    .setProjection(Projections.rowCount());
            return new QueryResult<Entity>((List<Entity>)criteria.list(),
                    ((Long)countCriteria.uniqueResult()).intValue());  //ninja API change fro Hibernat
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

    @Override
    public Entity getByPrimaryKey(Serializable key) throws MusicServiceException {
        return this.get((Key)key);
    }
    
    public QueryResult<Entity> search(Map<Facet<Entity>, Object> terms, Facet<Entity> sortColumn, SortOrder sortOrder, int firstResultIndex, int maxResultSize) throws MusicServiceException {
        Session session = getSession();
        Transaction transaction = beginTransaction(session);
        try{
            Criteria criteria = session.createCriteria(entityClass);
            Criteria countCriteria = session.createCriteria(entityClass);
            for(Facet<Entity> facet : terms.keySet()){
                SearchHandle handle = facet.getHandle(SearchHandle.class);
                Object term = terms.get(facet);
                if(term != null){
                    handle.createFilter(criteria, term);
                    handle.createFilter(countCriteria, term);
                }
            }
            if(sortColumn != null){
                sortColumn.getHandle(SearchHandle.class).createOrder(criteria, sortOrder);
            }
            criteria.setMaxResults(maxResultSize)
                    .setFirstResult(firstResultIndex);
            List<Entity> results = (List<Entity>)criteria.list();
            int count = ((Long)countCriteria.setProjection(Projections.rowCount()).uniqueResult()).intValue(); //damn you hibernate
            return new QueryResult<Entity>(results, count);
        }catch(Exception e){
            rollback(transaction);
            throw new MusicServiceException("unable to update score", e);
        }finally{
            close(session, transaction);
        }
    }
    
     public QueryResult<Entity> search(Map<Facet<Entity>, Object> terms, Facet<Entity> sortColumn, SortOrder sortOrder) throws MusicServiceException {
        Session session = getSession();
        Transaction transaction = beginTransaction(session);
        try{
            Criteria criteria = session.createCriteria(entityClass);
            Criteria countCriteria = session.createCriteria(entityClass);
            for(Facet<Entity> facet : terms.keySet()){
                SearchHandle handle = facet.getHandle(SearchHandle.class);
                Object term = terms.get(facet);
                if(term != null){
                    handle.createFilter(criteria, term);
                    handle.createFilter(countCriteria, term);
                }
            }
            if(sortColumn != null){
                sortColumn.getHandle(SearchHandle.class).createOrder(criteria, sortOrder);
            }
            List<Entity> results = (List<Entity>)criteria.list();
            int count = ((Long)countCriteria.setProjection(Projections.rowCount()).uniqueResult()).intValue(); //damn you hibernate
            return new QueryResult<Entity>(results, count);
        }catch(Exception e){
            rollback(transaction);
            throw new MusicServiceException("unable to update score", e);
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
