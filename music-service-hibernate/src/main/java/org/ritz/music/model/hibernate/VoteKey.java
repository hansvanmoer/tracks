/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.model.hibernate;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author hans
 */
public class VoteKey implements Serializable{
    
    private Long userId;
    private Long trackId;

    public VoteKey(){}
    
    public VoteKey(Long userId, Long trackId){
        this.userId = userId;
        this.trackId = trackId;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.userId);
        hash = 23 * hash + Objects.hashCode(this.trackId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VoteKey other = (VoteKey) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.trackId, other.trackId)) {
            return false;
        }
        return true;
    }
    
}
