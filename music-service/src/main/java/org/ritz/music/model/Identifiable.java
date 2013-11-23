/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.model;

import java.io.Serializable;

/**
 *
 * @author hans
 */
public interface Identifiable extends Serializable{
    
    Object getUniqueId();
    
}
