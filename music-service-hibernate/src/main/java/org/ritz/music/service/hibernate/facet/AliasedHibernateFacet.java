/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service.hibernate.facet;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.ritz.music.service.SortOrder;

/**
 *
 * @author hans
 */
public class AliasedHibernateFacet implements HibernateFacet{
    
    private String foreignKey;
    private String entityName;
    private String propertyName;

    public AliasedHibernateFacet(String foreignKey, String entityName, String propertyName) {
        this.foreignKey = foreignKey;
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    @Override
    public Criteria addOrder(Criteria criteria, SortOrder sortOrder) {
        criteria.createAlias(foreignKey, entityName);
        if(sortOrder == SortOrder.ASCENDING){
            return criteria.addOrder(Order.asc(propertyName));
        }else{
            return criteria.addOrder(Order.desc(propertyName));
        }
    }
    
    
    
}
