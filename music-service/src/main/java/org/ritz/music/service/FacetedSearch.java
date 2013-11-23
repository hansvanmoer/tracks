/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service;

import java.io.Serializable;
import java.util.Map;
import org.ritz.music.facet.Facet;
import org.ritz.music.model.QueryResult;

/**
 *
 * @author hans
 */
public interface FacetedSearch<Model extends Serializable> {
    
    public Model getByPrimaryKey(Serializable primaryKey) throws MusicServiceException;
    
    public QueryResult<Model> search(Map<Facet<Model> , Object > terms, Facet<Model> sortColumn, SortOrder sortOrder, int firstResultIndex, int maxResultSize) throws MusicServiceException;
    
    public QueryResult<Model> search(Map<Facet<Model> , Object > terms, Facet<Model> sortColumn, SortOrder sortOrder) throws MusicServiceException;
    
}
