/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service.hibernate.facet;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ritz.music.service.SortOrder;

/**
 *
 * @author hans
 */
public class BasicSearchHandle implements SearchHandle{
    
    private String propertyName;
    private FilterOperator operator;
    
    public BasicSearchHandle(String propertyName, FilterOperator operator){
        this.propertyName = propertyName;
        this.operator = operator;
    }

    @Override
    public Criteria createOrder(Criteria criteria, SortOrder order) {
        if(order == SortOrder.ASCENDING){
            return criteria.addOrder(Order.asc(propertyName));
        }else{
            return criteria.addOrder(Order.desc(propertyName));
        }
    }

    @Override
    public Criteria createFilter(Criteria criteria, Object value) {
        switch(operator){
            case EQUALS:
                return criteria.add(Restrictions.eq(propertyName, value));
            default:
                return criteria.add(Restrictions.like(propertyName, "%"+value+"%"));
        }
    }
    
    
    
}
