// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import java.util.List;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.service.AbstractSetCacheService;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dto.ActivityWorksExampleInfo;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
public abstract class ActivityWorksExampleInfoService extends AbstractSetCacheService<ActivityWorksExampleInfo> {

    public abstract List<ActivityWorksExampleInfo> listInfo(int activityId) throws ServiceException;

    public static class ActivityWorksExampleInfoSetCacheFilter extends StartSizeCacheFilter {
        private int activityId;

        public ActivityWorksExampleInfoSetCacheFilter(int activityId) {
            this.activityId = activityId;
            super.setStart(0);
            super.setSize(Integer.MAX_VALUE);
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }
    }

}
