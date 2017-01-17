// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.cache.CacheFilter.SizeCacheFilter;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.CollectionUtils;
import com.jmcxclub.dream.family.dao.ActivityWorksExampleInfoDao;
import com.jmcxclub.dream.family.dto.ActivityWorksExampleInfo;
import com.jmcxclub.dream.family.service.ActivityWorksExampleInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
@Service("activityWorksExampleInfoService")
public class ActivityWorksExampleInfoServiceImpl extends ActivityWorksExampleInfoService {
    private static final Logger log = Logger.getLogger(ActivityWorksExampleInfoServiceImpl.class);
    @Autowired
    private ActivityWorksExampleInfoDao activityWorksExampleInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;
    private String setKey = "activity:works:example:ids";
    private String infoKey = "activity:works:example:info";


    @Override
    protected String buildSetKey(SizeCacheFilter filter) {
        ActivityWorksExampleInfoSetCacheFilter curFilter = (ActivityWorksExampleInfoSetCacheFilter) filter;
        return RedisCacheUtils.buildKey(setKey, curFilter.getActivityId());
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected SizeCacheFilter buildCacheFilter(ActivityWorksExampleInfo value) {
        return new ActivityWorksExampleInfoSetCacheFilter(value.getActivityId());
    }

    @Override
    protected LoadDao<ActivityWorksExampleInfo> getLoadDao() {
        return activityWorksExampleInfoDao;
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
    public CommonDao<ActivityWorksExampleInfo> getCommonDao() {
        return activityWorksExampleInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public List<ActivityWorksExampleInfo> listInfo(int activityId) throws ServiceException {
        SizeCacheFilter filter = new ActivityWorksExampleInfoSetCacheFilter(activityId);
        String key = buildSetKey(filter);
        List<Integer> ids = RedisCacheUtils.smemberIds(key, getJedisPool());
        if (CollectionUtils.emptyOrNull(ids)) {
            return new ArrayList<ActivityWorksExampleInfo>();
        }
        List<ActivityWorksExampleInfo> infos = getData(ids);
        return infos;
    }

}
