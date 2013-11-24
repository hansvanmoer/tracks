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

    private static final String VOTING_ENABLED = "votingEnabled";
    private static final String DISPLAY_RESULTS = "displayScores";
    private static final String ANSWER = "answer";
    
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
    
    public Integer getInteger(String key, Integer defaultValue) throws MusicServiceException {
        String value = settingService.getSettingValue(key);
        if (value == null) {
            return defaultValue;
        } else {
            return Integer.parseInt(value);
        }
    }
    
    public int getAnswer() throws MusicServiceException{
        return getInteger(ANSWER, 1);
    }
    
    public void setAnswer(int answer) throws MusicServiceException{
        settingService.setSetting(ANSWER, answer);
    }

    public boolean isVotingEnabled() throws MusicServiceException{
        return getBoolean(VOTING_ENABLED, true);
    }
    
    public void setVotingEnabled(boolean votingEnabled) throws MusicServiceException{
        settingService.setSetting(VOTING_ENABLED, votingEnabled);
    }
    
    public boolean isDisplayResults() throws MusicServiceException{
        return getBoolean(DISPLAY_RESULTS, false);
    }
    
    public void setDisplayResults(boolean displayResults) throws MusicServiceException{
        settingService.setSetting(DISPLAY_RESULTS, displayResults);
    }
    
    public SettingService getSettingService() {
        return settingService;
    }

    public void setSettingService(SettingService settingService) {
        this.settingService = settingService;
    }
}
