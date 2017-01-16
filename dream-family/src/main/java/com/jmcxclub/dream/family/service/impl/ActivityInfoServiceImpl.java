// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.ActivityInfoDao;
import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.service.ActivityInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("activityInfoService")
public class ActivityInfoServiceImpl extends ActivityInfoService {
    private static final Logger log = Logger.getLogger(ActivityInfoServiceImpl.class);
    @Autowired
    private ActivityInfoDao activityInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String sortedIdsKey = "activity:ids";
    private String infoKey = "activity:info";


    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        return sortedIdsKey;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(ActivityInfo value) {
        return new StartSizeCacheFilter();
    }

    @Override
    protected LoadDao<ActivityInfo> getLoadDao() {
        return activityInfoDao;
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
    public CommonDao<ActivityInfo> getCommonDao() {
        return activityInfoDao;
    }

    @Override
    protected int getPriority() {
        return 2;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public void modifyFinish(int id, int finish) throws ServiceException {
        ActivityInfo g = new ActivityInfo();
        g.setId(id);
        g.setFinish(finish);
        try {
            this.activityInfoDao.updateFinish(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        afterModifyData(g);
    }
}
