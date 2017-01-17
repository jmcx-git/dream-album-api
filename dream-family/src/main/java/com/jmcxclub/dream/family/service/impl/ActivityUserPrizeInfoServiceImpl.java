// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.cache.CacheFilter.SizeCacheFilter;
import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.CollectionUtils;
import com.jmcxclub.dream.family.dao.ActivityUserPrizeInfoDao;
import com.jmcxclub.dream.family.dto.ActivityUserPrizeInfo;
import com.jmcxclub.dream.family.service.ActivityUserPrizeInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
@Service("activityUserPrizeInfoService")
public class ActivityUserPrizeInfoServiceImpl extends ActivityUserPrizeInfoService {
    @Autowired
    private ActivityUserPrizeInfoDao activityUserPrizeInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String idsKey = "activity:user:prize:ids";
    private String infoKey = "activity:user:prize:info";

    @Override
    public int minUnbuildId() throws ServiceException {
        try {
            return activityUserPrizeInfoDao.queryMinUnbuildId();
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public void modifyBuild(List<ActivityUserPrizeInfo> datas) throws ServiceException {
        try {
            activityUserPrizeInfoDao.updateBuild(datas);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    protected String buildSetKey(SizeCacheFilter filter) {
        ActivityUserPrizeInfoSetCacheFilter curFilter = (ActivityUserPrizeInfoSetCacheFilter) filter;
        return RedisCacheUtils.buildKey(idsKey, curFilter.getActivityId());
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(ActivityUserPrizeInfo value) {
        return new ActivityUserPrizeInfoSetCacheFilter(value.getActivityId());
    }

    @Override
    protected LoadDao<ActivityUserPrizeInfo> getLoadDao() {
        return activityUserPrizeInfoDao;
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
    public CommonDao<ActivityUserPrizeInfo> getCommonDao() {
        return activityUserPrizeInfoDao;
    }

    @Override
    public List<ActivityUserPrizeInfo> listInfo(int activityId) throws ServiceException {
        SizeCacheFilter filter = new ActivityUserPrizeInfoSetCacheFilter(activityId);
        String key = buildSetKey(filter);
        List<Integer> ids = RedisCacheUtils.smemberIds(key, getJedisPool());
        if (CollectionUtils.emptyOrNull(ids)) {
            return new ArrayList<ActivityUserPrizeInfo>();
        }
        List<ActivityUserPrizeInfo> infos = getData(ids);
        return infos;
    }


}
