/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.ritz.music.model.QueryResult;
import org.ritz.music.model.Track;
import org.ritz.music.model.Vote;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.TrackService;
import org.ritz.music.service.hibernate.facet.BasicSearchHandle;
import org.ritz.music.service.hibernate.facet.FilterOperator;
import org.ritz.music.service.hibernate.facet.ManyToOneSearchHandle;
import org.ritz.music.service.hibernate.facet.SearchHandle;

/**
 *
 * @author hans
 */
public class HibernateTrackService extends HibernateService<Track, Long> implements TrackService, Serializable{
    
    private static final int TRACKS_PER_RUN = 100;
    
    public HibernateTrackService(){
        super(Track.class);
        if(!Track.TITLE_FACET.hasHandle(SearchHandle.class)){
            Track.TITLE_FACET.setHandle(SearchHandle.class, new BasicSearchHandle("title", FilterOperator.LIKE));
            Track.ARTIST_FACET.setHandle(SearchHandle.class, new ManyToOneSearchHandle("artist", "artist_" ,"name", FilterOperator.LIKE));
            Track.SCORE_FACET.setHandle(SearchHandle.class, new BasicSearchHandle("score", FilterOperator.EQUALS));
        }
    }
    
    @Override
    public void addTrack(Track track) throws MusicServiceException {
        super.saveOrUpdate(track);
    }

    @Override
    public List<Track> getTracks() throws MusicServiceException {
        return super.getAll();
    }

    @Override
    public QueryResult<Track> getTracks(int firstResultIndex, int maxResultCount) throws MusicServiceException {
        return super.getList(ALL_RESULTS, firstResultIndex, maxResultCount);
    }

    @Override
    public QueryResult<Track> getTracks(Collection<String> searchTerms, int firstResultIndex, int maxResultCount) throws MusicServiceException {
        return super.getList(new SearchTracks(searchTerms), firstResultIndex, maxResultCount);
    }

    @Override
    public List<Track> getTracks(Collection<String> searchTerms) throws MusicServiceException {
        return super.getList(new SearchTracks(searchTerms));
    }

    @Override
    public Track getTrack(Long trackId) throws MusicServiceException {
        return super.get(trackId);
    }
    
    @Override
    public void updateScores() throws MusicServiceException {
        Session session = getSession();
        Transaction transaction = beginTransaction(session);
        try{
            int index = 0;
            boolean next = true;
            while(next){
                List<Track> tracks = session.createCriteria(Track.class).setFirstResult(index).setMaxResults(TRACKS_PER_RUN).list();
                for(Track track : tracks){
                    track.setScore((Integer)session.createCriteria(Vote.class)
                            .add(Restrictions.eq("trackId", track.getTrackId()))
                            .setProjection(Projections.sum("score"))
                        .uniqueResult());
                    session.saveOrUpdate(track);
                }
                next = tracks.size() == TRACKS_PER_RUN;
                index+=TRACKS_PER_RUN;
            }
        }catch(Exception e){
            rollback(transaction);
            throw new MusicServiceException("unable to update score", e);
        }finally{
            close(session, transaction);
        }
    }

    @Override
    public List<Track> getTracksByScore() throws MusicServiceException {
        return super.getList(ALL_TRACKS);
    }
    
    @Override
    public QueryResult<Track> getTracksByScore(int firstResultIndex, int maxResultCount) throws MusicServiceException {
        return super.getList(ALL_TRACKS, firstResultIndex, maxResultCount);
    }
    
    private static class AllTracks implements CriteriaPreparator{

        @Override
        public Criteria prepare(Criteria criteria) {
            return criteria.addOrder(Order.asc("score")).addOrder(Order.asc("title"));
        }
        
    }
    
    private static CriteriaPreparator ALL_TRACKS = new AllTracks();
    
    private static class SearchTracks implements CriteriaPreparator{
        
        private Collection<String> searchTerms;
        
        public SearchTracks(Collection<String> searchTerms){
            this.searchTerms = searchTerms;
        }
        
        public Criteria prepare(Criteria criteria){
            if(!searchTerms.isEmpty()){
                Iterator<String> i = searchTerms.iterator();
                Criterion crit = Restrictions.ilike("keywords", "%"+i.next()+"%");
                while(i.hasNext()){
                    crit = Restrictions.and(Restrictions.ilike("keywords", "%"+i.next()+"%"));
                }
                criteria.add(crit);
            }
            criteria.addOrder(Order.asc("title"));
            return criteria;
        }
        
    }
    
}
