// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.jmcxclub.dream.family.dao.ActivityVoteStatInfoDao;
import com.jmcxclub.dream.family.dto.ActivityVoteStatInfo;
import com.jmcxclub.dream.family.service.ActivityVoteStatInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("activityVoteStatInfoService")
public class ActivityWorksStatInfoServiceImpl extends ActivityVoteStatInfoService {
    private static final Logger log = Logger.getLogger(ActivityWorksStatInfoServiceImpl.class);
    @Autowired
    private ActivityVoteStatInfoDao activityVoteStatInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String sortedSetPrefixKey = "acti:vote:stat:ids";
    private String infoKey = "activotestat:info";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        ActivityVoteStatInfoSortedListCacheFilter curFilter = (ActivityVoteStatInfoSortedListCacheFilter) filter;
        return RedisCacheUtils.buildKey(sortedSetPrefixKey, curFilter.getActivityId());
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(ActivityVoteStatInfo value) {
        return new ActivityVoteStatInfoSortedListCacheFilter(value.getActivityId(), 0, 0);
    }

    @Override
    protected LoadDao<ActivityVoteStatInfo> getLoadDao() {
        return activityVoteStatInfoDao;
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
    public CommonDao<ActivityVoteStatInfo> getCommonDao() {
        return activityVoteStatInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected double buildSortedSetScore(ActivityVoteStatInfo t) {
        return t.getVotes();
    }

    @Override
    public void incr(int id, int activityId) throws ServiceException {
        ActivityVoteStatInfo g = new ActivityVoteStatInfo();
        g.setId(id);
        g.setActivityId(activityId);
        try {
            activityVoteStatInfoDao.insertOrUpdate(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    // modify score and info

    @Override
    public int rank(int activityId, int worksId) throws ServiceException {
        // 因为队列是从小到大排 所以采用zrevrank
        Long rank = RedisCacheUtils.zrevrank(buildSortedSetKey(new ActivityVoteStatInfoSortedListCacheFilter(
                activityId, 0, 0)), String.valueOf(worksId), getJedisPool());
        if (rank == null) {
            return -1;
        }
        return rank.intValue() + 1;
    }

    @Override
    protected void afterModifyData(List<ActivityVoteStatInfo> gg) {
        // remove infos
        List<String> keys = new ArrayList<String>();
        List<Integer> ids = new ArrayList<Integer>();
        for (ActivityVoteStatInfo g : gg) {
            keys.add(buildDataInfoKey(g.getId()));
            ids.add(g.getId());
        }
        RedisCacheUtils.del(keys, shardedJedisPool);
        deltaCacheInitLoad(ids);
    }

    @Override
    protected void afterAddData(List<ActivityVoteStatInfo> gg) {
        this.afterModifyData(gg);
    }

}
