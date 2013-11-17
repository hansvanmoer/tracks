/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music;

/**
 *
 * @author hans
 */
public class MusicException extends Exception{

    public MusicException() {
    }

    public MusicException(String message) {
        super(message);
    }

    public MusicException(String message, Throwable cause) {
        super(message, cause);
    }

    public MusicException(Throwable cause) {
        super(cause);
    }
    
}
