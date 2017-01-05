package com.dream.album.service.impl;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dream.album.dao.AlbumItemEditInfoDao;
import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.AlbumItemEditInfo;
import com.dreambox.core.service.album.AlbumItemEditInfoService;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
@Service("albumItemEditInfoService")
public class AlbumItemEditInfoServiceImpl extends AlbumItemEditInfoService {
    private static final Logger log = Logger.getLogger(AlbumItemEditInfoServiceImpl.class);
    @Autowired
    private AlbumItemEditInfoDao albumItemEditInfoDao;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "album:item:edit:info";

    @PostConstruct
    public void initCache() {
        this.cacheInitLoad();
    }

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        return null;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(AlbumItemEditInfo value) {
        return null;
    }

    @Override
    protected LoadDao<AlbumItemEditInfo> getLoadDao() {
        return albumItemEditInfoDao;
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
    public CommonDao<AlbumItemEditInfo> getCommonDao() {
        return albumItemEditInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public AlbumItemEditInfo getAlbumItemEditInfoByUk(AlbumItemEditInfo info) throws ServiceException {
        try {
            return albumItemEditInfoDao.queryAlbumItemEditInfoByUk(info);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }
}
