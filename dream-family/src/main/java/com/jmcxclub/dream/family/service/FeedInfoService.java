// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.jmcxclub.dream.family.dto.FeedInfo;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public abstract class FeedInfoService extends AbstractSortedListCacheService<FeedInfo> {

    public static class FeedInfoSortedListCacheFilter extends StartSizeCacheFilter {
        private Integer userId;
        private Integer spaceId;

        public FeedInfoSortedListCacheFilter(Integer userId, Integer spaceId, int start, int size) {
            super();
            this.userId = userId;
            this.spaceId = spaceId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getSpaceId() {
            return spaceId;
        }

        public void setSpaceId(Integer spaceId) {
            this.spaceId = spaceId;
        }
    }
}
