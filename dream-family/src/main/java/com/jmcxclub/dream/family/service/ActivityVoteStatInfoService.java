// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dto.ActivityVoteStatInfo;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public abstract class ActivityVoteStatInfoService extends AbstractSortedListCacheService<ActivityVoteStatInfo> {
    public static class ActivityVoteStatInfoSortedListCacheFilter extends StartSizeCacheFilter {
        private int activityId;

        public ActivityVoteStatInfoSortedListCacheFilter() {
        }

        public ActivityVoteStatInfoSortedListCacheFilter(int activityId, int start, int size) {
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

    public abstract void incr(int worksId, int activityId) throws ServiceException;

    public abstract int rank(int id) throws ServiceException;

}
