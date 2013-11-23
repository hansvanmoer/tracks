/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.facet;

import org.ritz.music.MusicException;

/**
 *
 * @author hans
 */
public class FacetException extends MusicException{

    public FacetException() {
    }

    public FacetException(String message) {
        super(message);
    }

    public FacetException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacetException(Throwable cause) {
        super(cause);
    }
    
}
