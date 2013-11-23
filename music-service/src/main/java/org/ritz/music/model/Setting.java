/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author hans
 */
public class Setting implements Serializable{
    private String settingId;
    private String value;
    private Timestamp created;
    private Timestamp modified;
    
    public Setting(){
        this.created = new Timestamp(System.currentTimeMillis());
        this.modified = this.created;
    }
    
    public Setting(String key, String value){
        this();
        this.settingId = key;
        this.value = value;
    }

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }
    
    public String getKey() {
        return settingId;
    }

    public void setKey(String key) {
        this.settingId = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }
}
