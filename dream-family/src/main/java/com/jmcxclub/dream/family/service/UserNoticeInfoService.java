// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.jmcxclub.dream.family.dto.UserNoticeInfo;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public abstract class UserNoticeInfoService extends AbstractSortedListCacheService<UserNoticeInfo> {
    public static class UserNoticeInfoSortedSetCacheFilter extends StartSizeCacheFilter {
        private int userId;

        public UserNoticeInfoSortedSetCacheFilter(int userId) {
            this.userId = userId;
            super.setStart(0);
            super.setSize(Integer.MAX_VALUE);
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

    }

}
