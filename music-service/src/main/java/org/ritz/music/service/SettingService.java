/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service;

import java.io.Serializable;

/**
 *
 * @author hans
 */
public interface SettingService {
    
    void setSetting(String key, Serializable value) throws MusicServiceException;

    String getSettingValue(String key) throws MusicServiceException;
    
}
