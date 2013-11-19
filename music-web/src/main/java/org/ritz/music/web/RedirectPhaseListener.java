/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class RedirectPhaseListener implements PhaseListener{

    private Logger LOG = LoggerFactory.getLogger(RedirectPhaseListener.class);
    
    @Override
    public void afterPhase(PhaseEvent pe) {
    }

    @Override
    public void beforePhase(PhaseEvent pe) {
        /*
        FacesContext context = pe.getFacesContext();
        Voter voter = (Voter) context.getApplication().evaluateExpressionGet(context, "#{voter}", Voter.class);
        if(voter != null && voter.hasVoted()){
            try{
                context.getExternalContext().redirect("confirm.xhtml");
            }catch(IOException e){
                LOG.error("unable to redirect to page 'confirm'", e);
            }
        }*/
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
}
