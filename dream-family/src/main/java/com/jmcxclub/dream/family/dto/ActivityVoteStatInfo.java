// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.StatusSerializable;

/**
 * 
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class ActivityVoteStatInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = -172768762311317947L;
    private int id;// reference works id
    private int activityId;// only for key
    private int votes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

}
