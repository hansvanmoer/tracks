/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.service.hibernate;

import java.io.Serializable;
import java.sql.Timestamp;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.ritz.music.model.Setting;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.SettingService;

/**
 *
 * @author hans
 */
public class HibernateSettingService extends HibernateService<Setting, String> implements SettingService{
    
    public HibernateSettingService(){
        super(Setting.class);
    }

    @Override
    public void setSetting(String key, Serializable value) throws MusicServiceException {
        Session session = super.getSession();
        Transaction transaction = super.beginTransaction(session);
        try{
            Setting setting = (Setting)super.get(key);
            if(setting == null){
                setting = new Setting(key, value.toString());
            }else{
                setting.setValue(value.toString());
                setting.setModified(new Timestamp(System.currentTimeMillis()));
            }
            session.saveOrUpdate(setting);
        }catch(HibernateException e){
            super.rollback(transaction);
        }finally{
            super.close(session, transaction);
        }
    }
    
    @Override
    public String getSettingValue(String key) throws MusicServiceException {
        Setting setting = super.get(key);
        if(setting == null){
            return null;
        }else{
            return setting.getValue();
        }
    }
    
}
