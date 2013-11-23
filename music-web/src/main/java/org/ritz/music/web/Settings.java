/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.SettingService;

/**
 *
 * @author hans
 */
public class Settings {

    private SettingService settingService;

    public Settings() {
    }

    public String getString(String key, String defaultValue) throws MusicServiceException {
        String value = settingService.getSettingValue(key);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    public Integer getString(String key, Integer defaultValue) throws MusicServiceException {
        String value = settingService.getSettingValue(key);
        if (value == null) {
            return defaultValue;
        } else {
            return Integer.parseInt(value);
        }
    }

    public Boolean getBoolean(String key, Boolean defaultValue) throws MusicServiceException {
        String value = settingService.getSettingValue(key);
        if (value == null) {
            return defaultValue;
        } else {
            return Boolean.parseBoolean(value);
        }
    }

    public SettingService getSettingService() {
        return settingService;
    }

    public void setSettingService(SettingService settingService) {
        this.settingService = settingService;
    }
}
