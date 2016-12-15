package com.dream.album.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dream.album.dao.UserAlbumItemInfoDao;
import com.dream.album.service.ImgService;
import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.album.UserAlbumItemInfoService;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
@Service("userAlbumItemInfoService")
public class UserAlbumItemInfoServiceImpl extends UserAlbumItemInfoService {
    private static final Logger log = Logger.getLogger(UserAlbumItemInfoServiceImpl.class);
    @Autowired
    private UserAlbumItemInfoDao userAlbumItemInfoDao;
    @Autowired
    private ImgService imgService;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "user:album:item:info";

    // private String listKey = "user:album:info:item:ids";

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

    @Override
    public String getPreviewImgPath(UserAlbumItemInfo g) {
        return imgService.getUserAlbumItemPreviewImgPath(g);
    }

    @Override
    public String getUserOriginImgPath(UserAlbumItemInfo g) {
        return imgService.getUserAlbumItemUserOriginImgPath(g);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public UserAlbumItemInfo getUserAlbumItemInfoByUk(UserAlbumItemInfo info) throws ServiceException {
        try {
            return userAlbumItemInfoDao.queryUserAlbumItemInfoByUk(info);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

}
