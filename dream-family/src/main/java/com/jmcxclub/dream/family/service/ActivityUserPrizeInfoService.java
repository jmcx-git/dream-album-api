// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import java.util.List;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.service.AbstractSetCacheService;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dto.ActivityUserPrizeInfo;

/**
 * 用户与活动的中奖关系列表
 * 
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public abstract class ActivityUserPrizeInfoService extends AbstractSetCacheService<ActivityUserPrizeInfo> {
    public abstract int minUnbuildId() throws ServiceException;

    public abstract void modifyBuild(List<ActivityUserPrizeInfo> datas) throws ServiceException;

    public static class ActivityUserPrizeInfoSetCacheFilter extends StartSizeCacheFilter {
        private int activityId;

        public ActivityUserPrizeInfoSetCacheFilter(int activityId) {
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
