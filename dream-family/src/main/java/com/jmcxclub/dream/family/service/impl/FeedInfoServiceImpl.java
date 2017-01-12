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
import com.jmcxclub.dream.family.dao.FeedInfoDao;
import com.jmcxclub.dream.family.dto.FeedInfo;
import com.jmcxclub.dream.family.service.FeedInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Service("feedInfoService")
public class FeedInfoServiceImpl extends FeedInfoService {
    private static final Logger log = Logger.getLogger(FeedInfoServiceImpl.class);
    @Autowired
    private FeedInfoDao feedInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String spaceFeedIdsKey = "space:feed:ids";
    private String userFeedIdsKey = "user:feed:ids";
    private String infoKey = "feed:info";

    @Override
    public void addData(FeedInfo g) throws ServiceException {
        int id;
        try {
            id = feedInfoDao.insertReturnId(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        g.setId(id);
        afterAddData(g);
    }

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        if (!(filter instanceof FeedInfoSortedListCacheFilter)) {
            throw ServiceException
                    .getInternalException("Unknown filter for cache. Need sortedListCacheFilter but found "
                            + filter.getClass().getName());
        }
        FeedInfoSortedListCacheFilter sortedListFilter = (FeedInfoSortedListCacheFilter) filter;
        if (sortedListFilter.getSpaceId() != null) {
            return buildSpaceSortedSetKey(sortedListFilter.getSpaceId());
        } else if (sortedListFilter.getUserId() != null) {
            return buildUserSortedSetKey(sortedListFilter.getUserId());
        }
        throw ServiceException.getInternalException("Unknown filter value cache. Filter:" + filter);
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(FeedInfo value) {
        return new FeedInfoSortedListCacheFilter(value.getUserId(), value.getSpaceId(), 0, 10);
    }

    @Override
    protected LoadDao<FeedInfo> getLoadDao() {
        return feedInfoDao;
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
    public CommonDao<FeedInfo> getCommonDao() {
        return feedInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected int getPriority() {
        return 3;
    }

    @Override
    protected List<String> buildSortedSetKeys(StartSizeCacheFilter filter) {
        FeedInfoSortedListCacheFilter curFilter = (FeedInfoSortedListCacheFilter) filter;
        List<String> keys = new ArrayList<String>();
        keys.add(buildUserSortedSetKey(curFilter.getUserId()));
        keys.add(buildSpaceSortedSetKey(curFilter.getSpaceId()));
        return keys;
    }

    private String buildSpaceSortedSetKey(Integer spaceId) {
        return RedisCacheUtils.buildKey(spaceFeedIdsKey, spaceId.intValue());
    }

    private String buildUserSortedSetKey(Integer userId) {
        return RedisCacheUtils.buildKey(userFeedIdsKey, userId.intValue());
    }

    @Override
    protected List<String> buildSortedSetMembers(FeedInfo t) {
        List<String> members = new ArrayList<String>();
        members.add(String.valueOf(t.getId()));
        members.add(String.valueOf(t.getId()));
        return members;
    }

    @Override
    protected List<Double> buildSortedSetScores(FeedInfo t) {
        List<Double> scores = new ArrayList<Double>();
        scores.add(Long.valueOf(t.getUpdateTime().getTime()).doubleValue());
        scores.add(Long.valueOf(t.getUpdateTime().getTime()).doubleValue());
        return scores;
    }

}
