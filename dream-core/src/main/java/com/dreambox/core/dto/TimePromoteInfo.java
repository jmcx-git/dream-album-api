// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.dto;

import java.io.Serializable;
import java.util.Date;

import com.dreambox.core.DbStatus;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class TimePromoteInfo extends DbStatus implements Serializable, Comparable<TimePromoteInfo> {
    /**
     * 
     */
    private static final long serialVersionUID = 8806793039171239371L;
    private Date startTime;
    private Date endTime;
    private int position;

    @Override
    public int compareTo(TimePromoteInfo o) {
        if (position - o.getPosition() == 0) {
            return super.getUpdateTime().compareTo(o.getUpdateTime());
        }
        return position - o.getPosition();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "TimePromoteInfo [startTime=" + startTime + ", endTime=" + endTime + ", position=" + position
                + ", toString()=" + super.toString() + "]";
    }

}
