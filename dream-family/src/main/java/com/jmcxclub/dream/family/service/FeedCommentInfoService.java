// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.jmcxclub.dream.family.dto.FeedCommentInfo;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public abstract class FeedCommentInfoService extends AbstractSortedListCacheService<FeedCommentInfo> {
    public static class CommentSortedSetCacheFilter extends StartSizeCacheFilter {
        private Integer userId;
        private Integer feedId;

        public CommentSortedSetCacheFilter(Integer userId, Integer feedId, int start, int size) {
            super();
            this.userId = userId;
            this.feedId = feedId;
            super.setSize(size);
            super.setStart(start);
            super.setReverse(false);
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getFeedId() {
            return feedId;
        }

        public void setFeedId(Integer feedId) {
            this.feedId = feedId;
        }
    }
}
