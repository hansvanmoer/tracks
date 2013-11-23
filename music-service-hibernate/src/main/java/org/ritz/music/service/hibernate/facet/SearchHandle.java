/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service.hibernate.facet;

import org.hibernate.Criteria;
import org.ritz.music.service.SortOrder;

/**
 *
 * @author hans
 */
public interface SearchHandle {
    
    Criteria createOrder(Criteria criteria, SortOrder order);
    
    Criteria createFilter(Criteria criteria, Object value);
}
