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
import com.jmcxclub.dream.family.dao.ActivityPrizeInfoDao;
import com.jmcxclub.dream.family.dto.ActivityPrizeInfo;
import com.jmcxclub.dream.family.service.ActivityPrizeInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("activityPrizeInfoService")
public class ActivityPrizeInfoServiceImpl extends ActivityPrizeInfoService {
    @Autowired
    private ActivityPrizeInfoDao activityPrizeInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String sortedIdsKey = "activity:prize:ids";
    private String infoKey = "activity:prize:info";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        ActivityPrizeInfoSortedSetCacheFilter curFilter = (ActivityPrizeInfoSortedSetCacheFilter) filter;
        return RedisCacheUtils.buildKey(sortedIdsKey, curFilter.getActivityId());
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(ActivityPrizeInfo value) {
        return new ActivityPrizeInfoSortedSetCacheFilter(value.getActivityId());
    }

    @Override
    protected LoadDao<ActivityPrizeInfo> getLoadDao() {
        return activityPrizeInfoDao;
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
    public CommonDao<ActivityPrizeInfo> getCommonDao() {
        return activityPrizeInfoDao;
    }

    @Override
    protected double buildSortedSetScore(ActivityPrizeInfo t) {
        return t.getRank();
    }
}
