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
public class BasicHibernateFacet implements HibernateFacet{
    
    private String propertyName;
    
    public BasicHibernateFacet(String propertyName){
        this.propertyName = propertyName;
    }

    @Override
    public Criteria addOrder(Criteria criteria, SortOrder order){
        if(order == SortOrder.ASCENDING){
            return criteria.addOrder(Order.asc(propertyName));
        }else{
            return criteria.addOrder(Order.desc(propertyName));
        }
    }

}
