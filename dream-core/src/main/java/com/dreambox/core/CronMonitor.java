// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core;

import java.util.Date;

/**
 * @author luofei@appchina.com create date: 2016年6月16日
 *
 */
public class CronMonitor {
    public CronMonitor(String name, boolean stop, String cron, String lastMessage, Date lastActiveTime) {
        super();
        this.name = name;
        this.stop = stop;
        this.cron = cron;
        this.lastMessage = lastMessage;
        this.lastActiveTime = lastActiveTime;
    }

    private String name;
    private boolean stop;
    private String cron;
    private String lastMessage;
    private Date lastActiveTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    @Override
    public String toString() {
        return "CronMonitor [name=" + name + ", cron=" + cron + ", lastMessage=" + lastMessage + ", lastActiveTime="
                + lastActiveTime + "]";
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

}
