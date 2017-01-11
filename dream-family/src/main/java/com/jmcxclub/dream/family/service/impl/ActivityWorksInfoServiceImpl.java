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
import com.jmcxclub.dream.family.dao.ActivityWorksInfoDao;
import com.jmcxclub.dream.family.dto.ActivityWorksInfo;
import com.jmcxclub.dream.family.service.ActivityWorksInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("activityWorksInfoService")
public class ActivityWorksInfoServiceImpl extends ActivityWorksInfoService {
    private static final Logger log = Logger.getLogger(ActivityWorksInfoServiceImpl.class);
    @Autowired
    private ActivityWorksInfoDao activityWorksInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String ukPkPrefixKey = "acti:works:pk:uk";
    private String infoPrefixKey = "acti:works:info";



    @Override
    protected int getPriority() {
        return 3;
    }

    @Override
    protected String buildUkPkPrefixKey() {
        return ukPkPrefixKey;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected Integer getIdByUkDriectFromDb(ActivityWorksInfo t) {
        try {
            return activityWorksInfoDao.queryIdByUk(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    protected LoadDao<ActivityWorksInfo> getLoadDao() {
        return activityWorksInfoDao;
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
    public CommonDao<ActivityWorksInfo> getCommonDao() {
        return activityWorksInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
