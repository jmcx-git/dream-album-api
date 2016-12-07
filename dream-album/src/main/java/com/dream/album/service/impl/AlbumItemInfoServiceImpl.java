// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dream.album.dao.AlbumItemInfoDao;
import com.dreambox.core.cache.CacheFilter.IdStartSizeCacheFilter;
import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.core.utils.RedisCacheUtils;

/**
 * @author mokous86@gmail.com create date: Dec 7, 2016
 *
 */
@Service("albumItemInfoService")
public class AlbumItemInfoServiceImpl extends AlbumItemInfoService {
    private static final Logger log = Logger.getLogger(AlbumItemInfoServiceImpl.class);
    @Autowired
    private AlbumItemInfoDao albumItemInfoDao;
//    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
//    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "album:item:info";
    private String listKey = "album:info:item:ids";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        IdStartSizeCacheFilter idFilter = (IdStartSizeCacheFilter) filter;
        return RedisCacheUtils.buildKey(listKey, idFilter.getId());
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(AlbumItemInfo value) {
        IdStartSizeCacheFilter idFilter = new IdStartSizeCacheFilter();
        idFilter.setId(value.getAlbumId());
        return idFilter;
    }

    @Override
    protected LoadDao<AlbumItemInfo> getLoadDao() {
        return albumItemInfoDao;
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
    public CommonDao<AlbumItemInfo> getCommonDao() {
        return albumItemInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

}
