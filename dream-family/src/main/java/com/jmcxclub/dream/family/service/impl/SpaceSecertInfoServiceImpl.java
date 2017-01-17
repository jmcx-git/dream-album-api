// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
@Service("spaceSecertInfoService")
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
    private String spaceSecertKey = "space:secert";
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
            String secert = RandomStringUtils.random(randomLen, true, true).toLowerCase();
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

    @Override
    public String getSecert(int spaceId) throws ServiceException {
        String secert = RedisCacheUtils.get(buildSpaceSecertKey(spaceId), getJedisPool());
        if (StringUtils.isEmpty(secert)) {
            try {
                secert = spaceSecertInfoDao.queryLastSpaceSecert(spaceId);
            } catch (SQLException e) {
                throw ServiceException.getSQLException(e);
            }
            if (StringUtils.isNotEmpty(secert)) {
                RedisCacheUtils.set(buildSpaceSecertKey(spaceId), secert, getJedisPool());
            }
        }
        return secert;
    }

    @Override
    protected int getPriority() {
        return 3;
    }

    @Override
    protected void afterAddData(SpaceSecertInfo g) {
        this.afterModifyData(g);
    }

    private String buildSpaceSecertKey(int spaceId) {
        return RedisCacheUtils.buildKey(spaceSecertKey, spaceId);
    }

    @Override
    protected void afterModifyData(SpaceSecertInfo g) {
        if (g.getSpaceId() > 0 && !StringUtils.isEmpty(g.getSecert())) {
            if (g.getStatus() == SpaceSecertInfo.STATUS_OK) {
                RedisCacheUtils.set(buildSpaceSecertKey(g.getSpaceId()), g.getSecert(), getJedisPool());
            } else {
                RedisCacheUtils.del(getJedisPool(), buildSpaceSecertKey(g.getSpaceId()));
            }
        } else {
            if (g.getSpaceId() > 0) {
                RedisCacheUtils.del(getJedisPool(), buildSpaceSecertKey(g.getSpaceId()));
            }
        }
        if (g.getId() > 0) {
            RedisCacheUtils.del(buildDataInfoKey(g.getId()), shardedJedisPool);
        }
    }
}
