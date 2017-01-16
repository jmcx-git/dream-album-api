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
import com.jmcxclub.dream.family.dao.UserSpaceRelationshipInfoDao;
import com.jmcxclub.dream.family.dto.SpaceRelationshipEnum;
import com.jmcxclub.dream.family.dto.SpaceTopEnum;
import com.jmcxclub.dream.family.dto.UserSpaceRelationshipInfo;
import com.jmcxclub.dream.family.service.UserSpaceRelationshipInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Service("userSpaceRelationshipInfoService")
public class UserSpaceRelationshipInfoServiceImpl extends UserSpaceRelationshipInfoService {
    private static final Logger log = Logger.getLogger(UserSpaceRelationshipInfoServiceImpl.class);
    // REDIS SUPPORT MAX SCORE -(2^53) and +(2^53) included.
    private static final double MAX_SCORE = Long.MAX_VALUE >> 12;
    private static final double MIDDLE_SCORE = Long.MAX_VALUE >> 13;
    private static final double MIN_SCORE = Long.MAX_VALUE / 14;
    @Autowired
    private UserSpaceRelationshipInfoDao userSpaceRelationshipInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String userIdsKey = "user:relationship:ids";
    private String spaceIdsKey = "space:relationship:ids";
    private String infoKey = "relationship:info";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        if (!(filter instanceof UserSpaceRelationshipInfoSortedListCacheFilter)) {
            throw ServiceException.getInternalException("Unknown cache filter. Filter:" + filter);
        }
        UserSpaceRelationshipInfoSortedListCacheFilter curFilter = (UserSpaceRelationshipInfoSortedListCacheFilter) filter;
        if (curFilter.getSpaceId() != null) {
            return buildSpaceIdListKey(curFilter.getSpaceId());
        }
        if (curFilter.getUserId() != null) {
            return buildUserIdListKey(curFilter.getUserId());
        }
        throw ServiceException.getInternalException("Unknown cache filter. Filter:" + filter);
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(UserSpaceRelationshipInfo value) {
        return new UserSpaceRelationshipInfoSortedListCacheFilter(value.getSpaceId(), value.getUserId(), 0, 10);
    }

    @Override
    protected LoadDao<UserSpaceRelationshipInfo> getLoadDao() {
        return userSpaceRelationshipInfoDao;
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
    public CommonDao<UserSpaceRelationshipInfo> getCommonDao() {
        return userSpaceRelationshipInfoDao;
    }

    @Override
    protected List<String> buildSortedSetMembers(UserSpaceRelationshipInfo t) {
        List<String> members = new ArrayList<String>();
        members.add(String.valueOf(t.getId()));
        members.add(String.valueOf(t.getId()));
        return members;
    }

    @Override
    protected List<String> buildSortedSetKeys(StartSizeCacheFilter filter) {
        if (!(filter instanceof UserSpaceRelationshipInfoSortedListCacheFilter)) {
            throw ServiceException.getInternalException("Unknown cache filter. Filter:" + filter);
        }
        UserSpaceRelationshipInfoSortedListCacheFilter curFilter = (UserSpaceRelationshipInfoSortedListCacheFilter) filter;
        List<String> keys = new ArrayList<String>();
        keys.add(buildUserIdListKey(curFilter.getUserId()));
        keys.add(buildSpaceIdListKey(curFilter.getSpaceId()));
        return keys;
    }

    @Override
    protected List<Double> buildSortedSetScores(UserSpaceRelationshipInfo t) {
        double userScoreList = 0d;
        double spaceIdListScore = t.getUpdateTime().getTime();
        if (t.getRelationship() == SpaceRelationshipEnum.OWNER.getFlag()) {
            userScoreList = MAX_SCORE + t.getSpaceId();
        } else if (t.getTop() == SpaceTopEnum.YES.getTop()) {
            userScoreList = MIDDLE_SCORE + t.getSpaceId();
        } else {
            // 越晚加入的权重越小
            userScoreList = MIN_SCORE - t.getSpaceId();
        }
        List<Double> scores = new ArrayList<Double>();
        scores.add(userScoreList);
        scores.add(spaceIdListScore);
        return scores;
    }

    private String buildUserIdListKey(Integer userId) {
        return RedisCacheUtils.buildKey(userIdsKey, userId);
    }

    private String buildSpaceIdListKey(Integer spaceId) {
        return RedisCacheUtils.buildKey(spaceIdsKey, spaceId);
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
    public boolean joinedSpace(int spaceId, int userId) throws ServiceException {
        UserSpaceRelationshipInfo g = new UserSpaceRelationshipInfo();
        g.setSpaceId(spaceId);
        g.setUserId(userId);
        g.setStatus(UserSpaceRelationshipInfo.STATUS_OK);
        Integer id = null;
        try {
            id = this.userSpaceRelationshipInfoDao.queryIdByUk(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        return id != null && id.intValue() > 0;
    }
}
