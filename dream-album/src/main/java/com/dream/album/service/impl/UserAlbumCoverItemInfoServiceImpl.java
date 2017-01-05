// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dream.album.dao.AlbumCoverItemInfoDao;
import com.dream.album.service.ImgService;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.AlbumCoverItemInfo;
import com.dreambox.core.service.album.AlbumCoverItemInfoService;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;

/**
 * @author mokous86@gmail.com create date: Jan 5, 2017
 *
 */
@Service("albumCoverItemInfoService")
public class UserAlbumCoverItemInfoServiceImpl extends AlbumCoverItemInfoService {
    @Autowired
    private AlbumCoverItemInfoDao albumCoverItemInfoDao;
    @Autowired
    private ImgService imgService;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "album:cover:item:info";
    private String pkUkPrefixKey = "album:cover:item:id:reflect:id:uk";

    @Override
    public String getEditImgPath(AlbumCoverItemInfo info) {
        return imgService.getAlbumCoverItemEditImgPath(info);
    }

    @Override
    public String getDefaultPreImgPath(AlbumCoverItemInfo info) {
        return imgService.getAlbumCoverItemDefaultPreImgPath(info);
    }

    @Override
    protected String buildUkPkPrefixKey() {
        return pkUkPrefixKey;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected Integer getIdByUkDriectFromDb(AlbumCoverItemInfo t) {
        try {
            return albumCoverItemInfoDao.queryIdByUk(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    protected LoadDao<AlbumCoverItemInfo> getLoadDao() {
        return albumCoverItemInfoDao;
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
    public CommonDao<AlbumCoverItemInfo> getCommonDao() {
        return albumCoverItemInfoDao;
    }

}
