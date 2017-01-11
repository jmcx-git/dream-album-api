// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.service.ActivityInfoService;

/**
 * @author mokous86@gmail.com
 * create date: Jan 11, 2017
 *
 
 */
@Service("activityInfoService")
public class ActivityInfoServiceImpl extends ActivityInfoService {

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected JedisPool getJedisPool() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(ActivityInfo value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected LoadDao<ActivityInfo> getLoadDao() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected boolean isStop() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected ShardedJedisPool getSharedJedisPool() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String buildDataInfoKey(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommonDao<ActivityInfo> getCommonDao() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int getPriority() {
        return 2;
    }
}