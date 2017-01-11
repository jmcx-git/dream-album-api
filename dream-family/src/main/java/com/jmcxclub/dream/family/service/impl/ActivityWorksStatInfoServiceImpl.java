// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.jmcxclub.dream.family.dao.ActivityWorksStatInfoDao;
import com.jmcxclub.dream.family.dto.ActivityWorksStatInfo;
import com.jmcxclub.dream.family.service.ActivityWorksStatInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("activityWorksStatInfoService")
public class ActivityWorksStatInfoServiceImpl extends ActivityWorksStatInfoService {
    private static final Logger log = Logger.getLogger(ActivityWorksStatInfoServiceImpl.class);
    @Autowired
    private ActivityWorksStatInfoDao activityWorksStatInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String sortedSetPrefixKey = "acti:works:stat:ids";
    private String infoKey = "relationship:info";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        ActivityWorksStatInfoSortedListCacheFilter curFilter = (ActivityWorksStatInfoSortedListCacheFilter) filter;
        return RedisCacheUtils.buildKey(sortedSetPrefixKey, curFilter.getActivityId());
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(ActivityWorksStatInfo value) {
        return new ActivityWorksStatInfoSortedListCacheFilter(value.getActivityId(), 0, 0);
    }

    @Override
    protected LoadDao<ActivityWorksStatInfo> getLoadDao() {
        return activityWorksStatInfoDao;
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
    public CommonDao<ActivityWorksStatInfo> getCommonDao() {
        return activityWorksStatInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected double buildSortedSetScore(ActivityWorksStatInfo t) {
        return t.getVotes();
    }

}
