/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.DragDropEvent;
import org.ritz.music.model.Track;
import org.ritz.music.model.User;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class TrackList implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(TrackList.class);
    private static final int MAX_SELECTED_TRACKS = 5;
    private static final int[] SCORES = {5, 4, 3, 2, 1};
    private TrackService trackService;
    private String searchQuery;
    private List<Track> tracks;
    private List<Track> selectedTracks;
    
    private User user;
    private Integer answer;
    
    /*
     * Workaround: JSF orderlist does not change underlying order.
     */
    private String selectedTrackCodes;

    public TrackList() {
        this.selectedTracks = new ArrayList<Track>(MAX_SELECTED_TRACKS);
        this.searchQuery = "";
        this.user = new User("", "", "");
    }

    public TrackService getTrackService() {
        return trackService;
    }

    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getSelectedTracks() {
        return selectedTracks;
    }

    public void setSelectedTracks(List<Track> selectedTracks) {
        this.selectedTracks = selectedTracks;
    }

    public String getSelectedTrackCodes() {
        return selectedTrackCodes;
    }

    public void setSelectedTrackCodes(String selectedTrackCodes) {
        this.selectedTrackCodes = selectedTrackCodes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
    
    @PostConstruct
    public void loadTracks() {
        try {
            this.tracks = trackService.getTracks();
        } catch (MusicServiceException e) {
            LOG.error("unable to fetch tracks", e);
        }
    }

    public void select(Track track) {
        if (this.selectedTracks.size() < MAX_SELECTED_TRACKS && !this.selectedTracks.contains(track)) {
            this.selectedTracks.add(track);
            this.update();
        }
    }

    public void deselect(Track track) {
        if (this.selectedTracks.contains(track)) {
            this.selectedTracks.remove(track);
        }
        this.update();
    }

    public void queryChanged() {
        update();
    }

    public void trackDropped(AjaxBehaviorEvent event) {
        Track track = ((Track) ((DragDropEvent) event).getData());
        select(track);
    }

    private Track getSelectedTrackById(long id) {
        for (Track track : selectedTracks) {
            if (track.getTrackId() == id) {
                return track;
            }
        }
        return null;
    }

    public void selectedTracksChanged() {
        List<Track> selected = new LinkedList<Track>();
        StringTokenizer tokens = new StringTokenizer(this.selectedTrackCodes, ",");
        while (tokens.hasMoreTokens()) {
            selected.add(getSelectedTrackById(Long.parseLong(tokens.nextToken())));
        }
        this.selectedTracks = selected;
    }

    public void update() {
        try {
            this.tracks = trackService.getTracks(parseSearchTerms(searchQuery));
            this.tracks.removeAll(this.selectedTracks);
        } catch (MusicServiceException e) {
            LOG.error("unable to update track list", e);
        }
    }

    private List<String> parseSearchTerms(String query) {
        String[] terms = query.split("\\W");
        List<String> result = new ArrayList<String>(terms.length);
        for (String term : terms) {
            if (!term.isEmpty()) {
                result.add(term);
            }
        }
        return result;
    }
}
