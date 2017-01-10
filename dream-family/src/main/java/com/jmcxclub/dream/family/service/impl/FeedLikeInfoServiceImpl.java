// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.FeedLikeInfoDao;
import com.jmcxclub.dream.family.dto.FeedLikeInfo;
import com.jmcxclub.dream.family.service.FeedLikeInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Service("feedLikeInfoService")
public class FeedLikeInfoServiceImpl extends FeedLikeInfoService {
    @Autowired
    private FeedLikeInfoDao feedLikeInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String spaceFeedIdsKey = "feed:like:ids";
    private String infoKey = "feed:like:info";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        if (!(filter instanceof LikeInfoIdSortedSetCacheFilter)) {
            throw ServiceException.getInternalException("Unknown cache filter. Filter:" + filter);
        }
        LikeInfoIdSortedSetCacheFilter curFilter = (LikeInfoIdSortedSetCacheFilter) filter;
        if (curFilter.getUserId() != null) {
            // TODO
        }
        if (curFilter.getFeedId() != null) {
            RedisCacheUtils.buildKey(spaceFeedIdsKey, curFilter.getFeedId());
        }
        return null;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(FeedLikeInfo value) {
        return new LikeInfoIdSortedSetCacheFilter(value.getUserId(), value.getFeedId(), 0, Integer.MAX_VALUE);
    }

    @Override
    protected LoadDao<FeedLikeInfo> getLoadDao() {
        return feedLikeInfoDao;
    }

    @Override
    protected boolean isStop() {
        return false;
    }

    @Override
    protected ShardedJedisPool getSharedJedisPool() {
        return shardedJedisPool;
    }

    @Override
    protected String buildDataInfoKey(int id) {
        return RedisCacheUtils.buildKey(infoKey, id);
    }

    @Override
    public CommonDao<FeedLikeInfo> getCommonDao() {
        return feedLikeInfoDao;
    }

}
