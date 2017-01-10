// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.SmallAppDeveloperInfo;
import com.dreambox.core.service.album.SmallAppDeveloperInfoService;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.SmallAppDeveloperInfoDao;

/**
 * @author mokous86@gmail.com create date: 2016年12月27日
 *
 */
@Service("smallAppDeveloperInfoService")
public class SmallAppDeveloperInfoServiceImpl extends SmallAppDeveloperInfoService {
    private static final Logger log = Logger.getLogger(SmallAppDeveloperInfoServiceImpl.class);
    @Autowired
    private SmallAppDeveloperInfoDao smallAppDeveloperInfoDao;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String idInfoKey = "sa:dev:info:id";
    private String pkUkPrefixKey = "sa:dev:info:id:uk";

    @PostConstruct
    public void initCache() {
        this.cacheInitLoad();
    }

    @Override
    protected ShardedJedisPool getSharedJedisPool() {
        return shardedJedisPool;
    }

    @Override
    protected String buildDataInfoKey(int id) {
        return RedisCacheUtils.buildKey(idInfoKey, id);
    }

    @Override
    public CommonDao<SmallAppDeveloperInfo> getCommonDao() {
        return smallAppDeveloperInfoDao;
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
    protected LoadDao<SmallAppDeveloperInfo> getLoadDao() {
        return smallAppDeveloperInfoDao;
    }

    @Override
    protected boolean isStop() {
        return false;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Integer getIdByUkDriectFromDb(SmallAppDeveloperInfo t) throws ServiceException {
        try {
            return smallAppDeveloperInfoDao.queryIdByUk(t.getAppId());
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

}
