// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.jmcxclub.dream.family.dto.ActivityWorksStatInfo;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public abstract class ActivityWorksStatInfoService extends AbstractSortedListCacheService<ActivityWorksStatInfo> {
    public static class ActivityWorksStatInfoSortedListCacheFilter extends StartSizeCacheFilter {
        private int activityId;

        public ActivityWorksStatInfoSortedListCacheFilter(int activityId, int start, int size) {
            super();
            this.activityId = activityId;
            super.setStart(start);
            super.setSize(size);
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }
    }

}
