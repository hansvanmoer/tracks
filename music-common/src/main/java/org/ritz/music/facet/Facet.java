/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.facet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author hans
 */
public class Facet<Model extends Serializable>{
    
    private Class modelType;
    private String name;
    private Map<Class, Object> handles;
    
    public Facet(Class modelType, String name){
        this.modelType = modelType;
        this.name = name;
        this.handles = new HashMap<Class, Object>();
    }

    public Class getModelType() {
        return modelType;
    }

    public String getName() {
        return name;
    }
    
    public <T> T getHandle(Class<T> type) throws FacetException{
        T handle = (T)handles.get(type);
        if(handle == null){
            throw new FacetException();
        }else{
            return handle;
        }
    }
    
    public boolean hasHandle(Class type){
        return handles.containsKey(type);
    }
    
    public void setHandle(Class key, Object handle){
        handles.put(key, handle);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.modelType);
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Facet<Model> other = (Facet<Model>) obj;
        if (!Objects.equals(this.modelType, other.modelType)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
