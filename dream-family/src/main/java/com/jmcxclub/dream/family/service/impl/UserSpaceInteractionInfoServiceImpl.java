// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.UserSpaceInteractionInfoDao;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;
import com.jmcxclub.dream.family.service.UserSpaceInteractionInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("userSpaceInteractionInfoService")
public class UserSpaceInteractionInfoServiceImpl extends UserSpaceInteractionInfoService {
    private static final Logger log = Logger.getLogger(UserSpaceInteractionInfoServiceImpl.class);
    @Autowired
    private UserSpaceInteractionInfoDao userSpaceInteractionInfoDao;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "u:s:inter:info";
    private String ukPkPrefixKey = "uk:userspace:id";

    @Override
    protected String buildUkPkPrefixKey() {
        return ukPkPrefixKey;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected Integer getIdByUkDriectFromDb(UserSpaceInteractionInfo t) {
        try {
            return userSpaceInteractionInfoDao.queryIdByUk(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    protected LoadDao<UserSpaceInteractionInfo> getLoadDao() {
        return userSpaceInteractionInfoDao;
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
    public CommonDao<UserSpaceInteractionInfo> getCommonDao() {
        return userSpaceInteractionInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public void incrViews(int userId, int spaceId) throws ServiceException {
        UserSpaceInteractionInfo t = new UserSpaceInteractionInfo();
        t.setUserId(userId);
        t.setSpaceId(spaceId);
        try {
            userSpaceInteractionInfoDao.updateIncrViews(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        delCacheInfo(t);
    }

    @Override
    public void incrLikes(int userId, int spaceId) throws ServiceException {
        UserSpaceInteractionInfo t = new UserSpaceInteractionInfo();
        t.setUserId(userId);
        t.setSpaceId(spaceId);
        try {
            userSpaceInteractionInfoDao.updateIncrLikes(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        delCacheInfo(t);
    }

    @Override
    public void decrLikes(int userId, int spaceId) throws ServiceException {
        UserSpaceInteractionInfo t = new UserSpaceInteractionInfo();
        t.setUserId(userId);
        t.setSpaceId(spaceId);
        try {
            userSpaceInteractionInfoDao.updateDecrLikes(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        delCacheInfo(t);
    }

    @Override
    public void incrComments(int userId, int spaceId) throws ServiceException {
        UserSpaceInteractionInfo t = new UserSpaceInteractionInfo();
        t.setUserId(userId);
        t.setSpaceId(spaceId);
        try {
            userSpaceInteractionInfoDao.updateIncrComments(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        delCacheInfo(t);
    }

    @Override
    public void decrComments(int userId, int spaceId) throws ServiceException {
        UserSpaceInteractionInfo t = new UserSpaceInteractionInfo();
        t.setUserId(userId);
        t.setSpaceId(spaceId);
        try {
            userSpaceInteractionInfoDao.updateDecrComments(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        delCacheInfo(t);
    }

    private void delCacheInfo(UserSpaceInteractionInfo g) {
        int id = getIdByUk(g);
        if (id > 0) {
            RedisCacheUtils.del(buildDataInfoKey(id), shardedJedisPool);
        }
    }

    @Override
    protected int getPriority() {
        return 3;
    }

    @Override
    protected void afterAddData(UserSpaceInteractionInfo g) {
        // uk pk的对应关系未找到时会自动从数据库获取，无须预载入
        return;
    }

}
