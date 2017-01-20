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
import com.jmcxclub.dream.family.dao.WikiInfoDao;
import com.jmcxclub.dream.family.dto.WikiInfo;
import com.jmcxclub.dream.family.service.WikiInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 20, 2017
 *
 */
@Service("wikiInfoService")
public class WikiInfoServiceImpl extends WikiInfoService {
    private static final Logger log = Logger.getLogger(WikiInfoServiceImpl.class);
    @Autowired
    private WikiInfoDao wikiInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String sortedIdsKey = "wiki:ids";
    private String infoKey = "wiki:info";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        return sortedIdsKey;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(WikiInfo value) {
        return new StartSizeCacheFilter();
    }

    @Override
    protected LoadDao<WikiInfo> getLoadDao() {
        return wikiInfoDao;
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
    public CommonDao<WikiInfo> getCommonDao() {
        return wikiInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

}
