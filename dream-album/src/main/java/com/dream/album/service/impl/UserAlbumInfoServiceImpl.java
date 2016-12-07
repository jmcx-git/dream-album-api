// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dream.album.dao.UserAlbumInfoDao;
import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.service.album.UserAlbumInfoService;
import com.dreambox.core.utils.RedisCacheUtils;

/**
 * @author mokous86@gmail.com create date: Dec 7, 2016
 *
 */
@Repository("userAlbumInfoService")
public class UserAlbumInfoServiceImpl extends UserAlbumInfoService {
    private static final Logger log = Logger.getLogger(UserAlbumInfoServiceImpl.class);
    @Autowired
    private UserAlbumInfoDao userAlbumInfoDao;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "user:album:info";
    private String listKey = "user:album:info:ids";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(UserAlbumInfo value) {
        return null;
    }

    @Override
    protected LoadDao<UserAlbumInfo> getLoadDao() {
        return this.userAlbumInfoDao;
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
    public CommonDao<UserAlbumInfo> getCommonDao() {
        return this.userAlbumInfoDao;
    }

}
