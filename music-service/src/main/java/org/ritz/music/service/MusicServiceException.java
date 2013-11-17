/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service;

import org.ritz.music.MusicException;

/**
 *
 * @author hans
 */
public class MusicServiceException extends MusicException{

    public MusicServiceException() {
    }

    public MusicServiceException(String message) {
        super(message);
    }

    public MusicServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MusicServiceException(Throwable cause) {
        super(cause);
    }
    
}
