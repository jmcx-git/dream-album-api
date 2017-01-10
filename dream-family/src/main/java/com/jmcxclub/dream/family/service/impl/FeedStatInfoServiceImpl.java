// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.FeedStatInfoDao;
import com.jmcxclub.dream.family.dto.FeedStatInfo;
import com.jmcxclub.dream.family.service.FeedStatInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public class FeedStatInfoServiceImpl extends FeedStatInfoService {
    @Autowired
    private FeedStatInfoDao feedStatInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;

    private String infoPrefixKey = "feed:stat:info";

    @Override
    protected ShardedJedisPool getSharedJedisPool() {
        return shardedJedisPool;
    }

    @Override
    protected String buildDataInfoKey(int id) {
        return RedisCacheUtils.buildKey(infoPrefixKey, id);
    }

    @Override
    public CommonDao<FeedStatInfo> getCommonDao() {
        return feedStatInfoDao;
    }

    @Override
    public void incrLikes(int id) throws ServiceException {
        try {
            feedStatInfoDao.updateIncrLikes(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        FeedStatInfo g = new FeedStatInfo();
        g.setId(id);
        afterModifyData(g);
    }

    @Override
    public void decrLikes(int id) throws ServiceException {
        try {
            feedStatInfoDao.updateDecrLikes(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        FeedStatInfo g = new FeedStatInfo();
        g.setId(id);
        afterModifyData(g);
    }

    @Override
    public void incrComments(int id) throws ServiceException {
        try {
            feedStatInfoDao.updateIncrComments(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        FeedStatInfo g = new FeedStatInfo();
        g.setId(id);
        afterModifyData(g);
    }

    @Override
    public void decrComments(int id) throws ServiceException {
        try {
            feedStatInfoDao.updateDecrComments(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        FeedStatInfo g = new FeedStatInfo();
        g.setId(id);
        afterModifyData(g);
    }

}
