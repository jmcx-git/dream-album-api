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
import com.jmcxclub.dream.family.dao.SystemNoticeInfoDao;
import com.jmcxclub.dream.family.dto.SystemNoticeInfo;
import com.jmcxclub.dream.family.service.SystemNoticeInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
@Service("systemNoticeInfoService")
public class SystemNoticeInfoServiceImpl extends SystemNoticeInfoService {
    @Autowired
    private SystemNoticeInfoDao systemNoticeInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;
    private String infoKey = "system:notice:info";
    private String sortedSetKey = "system:notice:ids";

    @Override
    protected ShardedJedisPool getSharedJedisPool() {
        return shardedJedisPool;
    }

    @Override
    protected String buildDataInfoKey(int id) {
        return RedisCacheUtils.buildKey(infoKey, id);
    }

    @Override
    public CommonDao<SystemNoticeInfo> getCommonDao() {
        return systemNoticeInfoDao;
    }

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        return sortedSetKey;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(SystemNoticeInfo value) {
        return new StartSizeCacheFilter();
    }

    @Override
    protected LoadDao<SystemNoticeInfo> getLoadDao() {
        return systemNoticeInfoDao;
    }

    @Override
    protected boolean isStop() {
        return false;
    }

    @Override
    protected double buildSortedSetScore(SystemNoticeInfo t) {
        return t.getCreateTime().getTime();
    }

}
