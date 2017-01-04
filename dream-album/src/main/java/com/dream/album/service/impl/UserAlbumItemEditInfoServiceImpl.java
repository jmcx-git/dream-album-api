package com.dream.album.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dream.album.dao.UserAlbumItemEditInfoDao;
import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.UserAlbumItemEditInfo;
import com.dreambox.core.service.album.UserAlbumItemEditInfoService;
import com.dreambox.core.utils.RedisCacheUtils;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
@Service("userAlbumItemEditInfoService")
public class UserAlbumItemEditInfoServiceImpl extends UserAlbumItemEditInfoService {
    private static final Logger log = Logger.getLogger(UserAlbumItemEditInfoServiceImpl.class);
    @Autowired
    private UserAlbumItemEditInfoDao userAlbumItemEditInfoDao;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "user:album:item:edit:info";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        return null;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(UserAlbumItemEditInfo value) {
        return null;
    }

    @Override
    protected LoadDao<UserAlbumItemEditInfo> getLoadDao() {
        return userAlbumItemEditInfoDao;
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
    public CommonDao<UserAlbumItemEditInfo> getCommonDao() {
        return userAlbumItemEditInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
