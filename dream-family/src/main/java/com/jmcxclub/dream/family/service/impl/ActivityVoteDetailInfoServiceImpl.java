// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.utils.DateUtils;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.ActivityVoteDetailInfoDao;
import com.jmcxclub.dream.family.dto.ActivityVoteDetailInfo;
import com.jmcxclub.dream.family.service.ActivityVoteDetailInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("activityVoteDetailInfoService")
public class ActivityVoteDetailInfoServiceImpl extends ActivityVoteDetailInfoService {
    @Autowired
    private ActivityVoteDetailInfoDao activityVoteDetailInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;

    private String ukPkPrefixKey = "acti:vote:detail:pk:uk";
    private String infoPrefixKey = "acti:vote:detail:info";
    private String userDayMaxVoteCountKey = "acti:vote:user:day:count";

    @Override
    public boolean addOrIgnoreData(ActivityVoteDetailInfo g) throws ServiceException {
        String key = RedisCacheUtils.buildKey(userDayMaxVoteCountKey, String.valueOf(g.getUserId()),
                DateUtils.getDateYMDStringValue(new Date()));
        if (RedisCacheUtils.incr(key, getJedisPool()) > 2) {
            return false;

        }
        RedisCacheUtils.expiresKey(key, 60 * 60 * 24, getJedisPool());
        return super.addOrIgnoreData(g);
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
    protected Integer getIdByUkDriectFromDb(ActivityVoteDetailInfo t) {
        Integer id = null;
        try {
            id = activityVoteDetailInfoDao.queryIdByUk(t);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        if (id != null && id.intValue() > 0) {
            t.setId(id);
            try {
                RedisCacheUtils.set(super.buildUkReflectPkKey(t), id.toString(), getJedisPool());
            } catch (Exception e) {
            }
        }
        return id;
    }

    @Override
    protected LoadDao<ActivityVoteDetailInfo> getLoadDao() {
        return activityVoteDetailInfoDao;
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
    public CommonDao<ActivityVoteDetailInfo> getCommonDao() {
        return activityVoteDetailInfoDao;
    }

}
