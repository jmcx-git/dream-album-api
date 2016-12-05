package com.dreambox.core.dto;

import java.util.Date;

/**
 * Created by zhouyanhui on 12/24/15.
 */
public class ScheduleRecord {
    private String scheduleName;
    private Date scheduleTime;
    private int startId;
    private Date continueDealTime;

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public int getStartId() {
        return startId;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }

    @Override
    public String toString() {
        return "ScheduleRecord{" +
                "scheduleName='" + scheduleName + '\'' +
                ", scheduleTime=" + scheduleTime +
                ", startId=" + startId +
                '}';
    }

    public void setContinueDealTime(Date continueDealTime) {
        this.continueDealTime = continueDealTime;
    }

    public Date getContinueDealTime() {
        return continueDealTime;
    }
}
