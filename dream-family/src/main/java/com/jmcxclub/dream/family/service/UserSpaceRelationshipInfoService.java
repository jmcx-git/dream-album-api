// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.jmcxclub.dream.family.dto.UserSpaceRelationshipInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 *         双向关系
 * 
 *         用户加入了哪些空间
 *
 *         空间存在哪些用户
 */
public abstract class UserSpaceRelationshipInfoService extends
        AbstractSortedListCacheService<UserSpaceRelationshipInfo> {

    public static class UserSpaceRelationshipInfoSortedListCacheFilter extends StartSizeCacheFilter {
        private Integer spaceId;
        private Integer userId;

        public UserSpaceRelationshipInfoSortedListCacheFilter(Integer spaceId, Integer userId, int start, int size) {
            super();
            this.spaceId = spaceId;
            this.userId = userId;
            super.setStart(start);
            super.setSize(size);
        }

        public Integer getSpaceId() {
            return spaceId;
        }

        public void setSpaceId(Integer spaceId) {
            this.spaceId = spaceId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }

}
