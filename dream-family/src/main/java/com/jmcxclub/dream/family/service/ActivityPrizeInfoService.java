// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.jmcxclub.dream.family.dto.ActivityPrizeInfo;

/**
 * 活动奖品服务
 * 
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public abstract class ActivityPrizeInfoService extends AbstractSortedListCacheService<ActivityPrizeInfo> {
    public static class ActivityPrizeInfoSortedSetCacheFilter extends StartSizeCacheFilter {
        private int activityId;

        public ActivityPrizeInfoSortedSetCacheFilter(int activityId) {
            this.activityId = activityId;
            super.setStart(0);
            super.setSize(Integer.MAX_VALUE);
            super.setReverse(false);
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }
    }

}
