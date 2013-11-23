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
public interface HibernateFacet {
    
    Criteria addOrder(Criteria criteria, SortOrder sortOrder);
    
}
