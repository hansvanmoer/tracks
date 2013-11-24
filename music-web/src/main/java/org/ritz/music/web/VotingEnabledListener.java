/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import org.jboss.logging.Logger;
import org.ritz.music.service.MusicServiceException;

/**
 *
 * @author hans
 */
public class VotingEnabledListener {
    
    private static final Logger LOG = Logger.getLogger(VotingEnabledListener.class);
    
    private Settings settings;
    
    public void handleEvent(ComponentSystemEvent event){
        try{
            if(!settings.isVotingEnabled()){
                FacesContext fc = FacesContext.getCurrentInstance();
                ExternalContext ec = fc.getExternalContext();
                ec.redirect(ec.getRequestContextPath()+"/result.xhtml");
                //fc.getApplication().getNavigationHandler().handleNavigation(fc,null, "result");
            }
        }catch(Exception e){
            LOG.error("unable to check setting 'isVotingEnabled'", e);
        }
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    
    
    
}
