/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.facet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hans
 */
public class FacetModel<Model extends Serializable, Data> {
    
    private Map<Facet<Model> , Data> data;
    
    public FacetModel(){
        this.data = new HashMap<Facet<Model>, Data>();
    }
    
    public FacetModel<Model, Data> add(Facet<Model> facet, Data data){
        this.data.put(facet, data);
        return this;
    }
    
    public Data get(Facet<Model> facet) throws FacetException{
        Data data = this.data.get(facet);
        if(data == null){
            throw new FacetException();
        }
        return data;
    }
    
    public static <Model extends Serializable, Data> FacetModel<Model, Data> create(Facet<Model> facet, Data data){
        FacetModel<Model, Data> model = new FacetModel<Model,Data>();
        return model.add(facet, data);
    }
    
}
