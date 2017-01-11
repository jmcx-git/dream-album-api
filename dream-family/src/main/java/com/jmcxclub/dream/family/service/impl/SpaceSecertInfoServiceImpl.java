// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.SpaceSecertInfoDao;
import com.jmcxclub.dream.family.dto.SpaceSecertInfo;
import com.jmcxclub.dream.family.service.SpaceSecertInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Service("SpaceSecertInfoService")
public class SpaceSecertInfoServiceImpl extends SpaceSecertInfoService {
    private static final int SECERT_LEN = 6;
    @Autowired
    private SpaceSecertInfoDao spaceSecertInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String ukPkPrefixKey = "space:secert:pk:uk";
    private String infoPrefixKey = "space:secert:info";

    @Override
    protected String buildUkPkPrefixKey() {
        return ukPkPrefixKey;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected Integer getIdByUkDriectFromDb(SpaceSecertInfo t) {
        try {
            return spaceSecertInfoDao.queryIdByUk(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    protected LoadDao<SpaceSecertInfo> getLoadDao() {
        return spaceSecertInfoDao;
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
        return RedisCacheUtils.buildKey(infoPrefixKey, id);
    }

    @Override
    public CommonDao<SpaceSecertInfo> getCommonDao() {
        return spaceSecertInfoDao;
    }

    @Override
    public String resetSecert(int spaceId) throws ServiceException {
        int randomLen = SECERT_LEN;
        try {
            spaceSecertInfoDao.updateStatusBySpaceId(spaceId);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        do {
            String secert = RandomStringUtils.random(randomLen);
            SpaceSecertInfo g = new SpaceSecertInfo();
            g.setSpaceId(spaceId);
            g.setSecert(secert);
            boolean success = addOrIgnoreData(g);
            if (success) {
                return secert;
            }
            randomLen += 1;
        } while (true);
    }
}
