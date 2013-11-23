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
public class ManyToOneSearchHandle implements SearchHandle{
    
    private String propertyName;
    private String alias;
    private String foreignName;
    private FilterOperator filterOperator;

    public ManyToOneSearchHandle(String propertyName, String alias, String foreignName, FilterOperator filterOperator) {
        this.propertyName = propertyName;
        this.alias = alias;
        this.foreignName = foreignName;
        this.filterOperator = filterOperator;
    }

    @Override
    public Criteria createOrder(Criteria criteria, SortOrder order) {
        criteria.createAlias(propertyName, alias);
        if(order == SortOrder.ASCENDING){
            return criteria.addOrder(Order.asc(alias+"."+foreignName));
        }else{
            return criteria.addOrder(Order.desc(alias+"."+foreignName));
        }
    }

    @Override
    public Criteria createFilter(Criteria criteria, Object value) {
        criteria.createAlias(propertyName, alias);
        switch(filterOperator){
            case EQUALS:
                return criteria.add(Restrictions.eq(alias+"."+foreignName, value));
            default:
                return criteria.add(Restrictions.like(alias+"."+foreignName, "%"+value+"%"));
        }
    }
    
    
    
    
}
