/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.ritz.music.facet.Facet;
import org.ritz.music.model.Identifiable;
import org.ritz.music.model.QueryResult;
import org.ritz.music.service.FacetedSearch;
import org.ritz.music.service.MusicServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class FacetedDataModel<Model extends Identifiable> extends LazyDataModel<Model> implements Serializable{
    
    private static Logger LOG = LoggerFactory.getLogger(FacetedDataModel.class);
    
    private List<Facet<Model> > facetList;
    private Map<String, Facet<Model> > facetMap;
    
    private FacetedSearch<Model> searchService;
    
    private Map<Facet<Model>, Object> searchTerms;
    
    private Facet<Model> sortFacet;
    
    private org.ritz.music.service.SortOrder sortOrder;
        
    public FacetedDataModel(FacetedSearch<Model> searchService){
        this.searchService = searchService;
        this.facetMap = new HashMap<String, Facet<Model> >();
        this.facetList = new LinkedList<Facet<Model> >();
        this.searchTerms = new HashMap<Facet<Model> , Object>();
        this.sortFacet = null;
        this.sortOrder = org.ritz.music.service.SortOrder.ASCENDING;
    }
    
    public FacetedDataModel(FacetedSearch<Model> searchService, Facet<Model>... facets){
        this(searchService);
        for(Facet<Model> facet : facets){
            this.facetMap.put(facet.getName(), facet);
            this.facetList.add(facet);
        }
    }
    
    public FacetedDataModel(FacetedSearch<Model> searchService, List<Facet<Model> > facets){
        this(searchService);
        for(Facet<Model> facet : facets){
            this.facetMap.put(facet.getName(), facet);
            this.facetList.add(facet);
        }
    }
    
    @Override
    public Object getRowKey(Model object) {
        return object.getUniqueId();
    }

    @Override
    public Model getRowData(String rowKey) {
        try{
            return searchService.getByPrimaryKey(Long.parseLong(rowKey));
        }catch(MusicServiceException e){
            LOG.error(String.format("unable to fetch model by primary key %s", rowKey), e);
            return null;
        }
    }
    
    private org.ritz.music.service.SortOrder getSortOrder(SortOrder order){
        switch(order){
            case ASCENDING:
                return org.ritz.music.service.SortOrder.ASCENDING;
            case DESCENDING:
                return org.ritz.music.service.SortOrder.DESCENDING;
            default:
                return org.ritz.music.service.SortOrder.ASCENDING;
        }
    }
    @Override
    public List<Model> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        try{
            for(String filter : filters.keySet()){
                searchTerms.put(facetMap.get(filter), filters.get(filter));
            }
            this.sortFacet = facetMap.get(sortField);
            this.sortOrder = getSortOrder(sortOrder);
            QueryResult<Model> result =  searchService.search(searchTerms, sortFacet, this.sortOrder, first, pageSize);
            this.setRowCount(result.getTotalResultCount());
            return result.getResults();
        }catch(MusicServiceException e){
            LOG.error("unable to perform faceted search", e);
            return new LinkedList<Model>();
        }
    }

    public FacetedSearch<Model> getSearchService() {
        return searchService;
    }

    public void setSearchService(FacetedSearch<Model> searchService) {
        this.searchService = searchService;
    }
    
    public List<Facet<Model> > getFacets(){
        return facetList;
    }

    public Map<Facet<Model>, Object> getSearchTerms() {
        return searchTerms;
    }

    public Facet<Model> getSortFacet() {
        return sortFacet;
    }

    public org.ritz.music.service.SortOrder getSortOrder() {
        return sortOrder;
    }
}
