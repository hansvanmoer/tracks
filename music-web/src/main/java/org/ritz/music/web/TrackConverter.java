/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.ritz.music.model.Track;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class TrackConverter implements javax.faces.convert.Converter{
    
    private static final Logger LOG = LoggerFactory.getLogger(TrackConverter.class);
    
    private TrackService trackService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        try{
            return trackService.getTrack(Long.parseLong(value));
        }catch(MusicServiceException e){
            LOG.error(String.format("unable to fetch track with id '%s'", value), e);
            return null;
        }catch(NumberFormatException e){
            LOG.error(String.format("invalid track id '%s'", value), e);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return Long.toString(((Track)o).getTrackId());
    }

    public TrackService getTrackService() {
        return trackService;
    }

    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }
}
