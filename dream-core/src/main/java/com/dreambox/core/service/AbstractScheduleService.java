// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.util.Date;

import com.dreambox.core.CronMonitor;

/**
 * @author luofei@appchina.com create date: 2016年6月15日
 *
 */
public abstract class AbstractScheduleService implements ScheduleService {
    public String getCron() {
        return "";
    }

    public String getLastMessage() {
        return "";
    }

    public Date getLastActiveTime() {
        return null;
    }

    public boolean isStop() {
        return false;
    }

    public CronMonitor getMonitor() {
        return new CronMonitor(getClass().getName(), isStop(), getCron(), getLastMessage(), getLastActiveTime());
    }

}
