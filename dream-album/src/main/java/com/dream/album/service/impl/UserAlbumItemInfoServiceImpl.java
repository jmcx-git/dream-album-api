package com.dream.album.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dream.album.dao.UserAlbumItemInfoDao;
import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.album.UserAlbumItemInfoService;
import com.dreambox.core.utils.RedisCacheUtils;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
public class UserAlbumItemInfoServiceImpl extends UserAlbumItemInfoService {
    private static final Logger log = Logger.getLogger(UserAlbumItemInfoServiceImpl.class);
    @Autowired
    private UserAlbumItemInfoDao userAlbumItemInfoDao;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "user:album:item:info";
    private String listKey = "user:album:info:item:ids";

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
    protected StartSizeCacheFilter buildCacheFilter(UserAlbumItemInfo value) {
        return null;
    }

    @Override
    protected LoadDao<UserAlbumItemInfo> getLoadDao() {
        return userAlbumItemInfoDao;
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
    public CommonDao<UserAlbumItemInfo> getCommonDao() {
        return userAlbumItemInfoDao;
    }

}
