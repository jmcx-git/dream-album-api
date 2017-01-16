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
import com.jmcxclub.dream.family.dao.UserNoticeInfoDao;
import com.jmcxclub.dream.family.dto.UserNoticeInfo;
import com.jmcxclub.dream.family.service.UserNoticeInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
@Service("userNoticeInfoService")
public class UserNoticeInfoServiceImpl extends UserNoticeInfoService {
    @Autowired
    private UserNoticeInfoDao userNoticeInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String sortedIdsKey = "user:notice:ids";
    private String infoKey = "user:notice:info";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        UserNoticeInfoSortedSetCacheFilter curFilter = (UserNoticeInfoSortedSetCacheFilter) filter;
        return RedisCacheUtils.buildKey(sortedIdsKey, curFilter.getUserId());
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(UserNoticeInfo value) {
        return new UserNoticeInfoSortedSetCacheFilter(value.getId());
    }

    @Override
    protected LoadDao<UserNoticeInfo> getLoadDao() {
        return userNoticeInfoDao;
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
    public CommonDao<UserNoticeInfo> getCommonDao() {
        return userNoticeInfoDao;
    }

}
