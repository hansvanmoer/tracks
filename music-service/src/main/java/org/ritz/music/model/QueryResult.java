/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author hans
 */
public class QueryResult<T extends Serializable> {
    
    private List<T> results;
    private int totalResultCount;

    public QueryResult(List<T> results, int totalResultCount){
        this.results = results;
        this.totalResultCount = totalResultCount;
    }
    
    public List<T> getResults() {
        return results;
    }

    public int getTotalResultCount() {
        return totalResultCount;
    }
}
