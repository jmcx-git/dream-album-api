// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.SpaceStatInfoDao;
import com.jmcxclub.dream.family.dto.SpaceStatInfo;
import com.jmcxclub.dream.family.service.SpaceStatInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Service("spaceStatInfoService")
public class SpaceStatInfoServiceImpl extends SpaceStatInfoService {
    @Autowired
    private SpaceStatInfoDao spaceStatInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;

    private String infoPrefixKey = "space:stat:info";

    @Override
    protected ShardedJedisPool getSharedJedisPool() {
        return shardedJedisPool;
    }

    @Override
    protected String buildDataInfoKey(int id) {
        return RedisCacheUtils.buildKey(infoPrefixKey, id);
    }

    @Override
    public CommonDao<SpaceStatInfo> getCommonDao() {
        return spaceStatInfoDao;
    }

    @Override
    public void incrViews(int id) throws ServiceException {
        try {
            spaceStatInfoDao.updateIncrViews(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        SpaceStatInfo g = new SpaceStatInfo();
        g.setId(id);
        afterModifyData(g);
    }

    @Override
    public void incrOccupants(int id) throws ServiceException {
        try {
            spaceStatInfoDao.updateIncrOccupants(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        SpaceStatInfo g = new SpaceStatInfo();
        g.setId(id);
        afterModifyData(g);
    }

    @Override
    public void decrOccupants(int id) throws ServiceException {
        try {
            spaceStatInfoDao.updateDecrOccupants(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        SpaceStatInfo g = new SpaceStatInfo();
        g.setId(id);
        afterModifyData(g);
    }

    @Override
    public void incrRecords(int id) throws ServiceException {
        try {
            spaceStatInfoDao.updateIncrRecords(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        SpaceStatInfo g = new SpaceStatInfo();
        g.setId(id);
        afterModifyData(g);
    }

    @Override
    public void decrRecords(int id) throws ServiceException {
        try {
            spaceStatInfoDao.updateDecrRecords(id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        SpaceStatInfo g = new SpaceStatInfo();
        g.setId(id);
        afterModifyData(g);
    }

}
