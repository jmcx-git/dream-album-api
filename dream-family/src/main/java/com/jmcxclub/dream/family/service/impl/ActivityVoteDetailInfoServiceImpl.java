// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
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

    @Override
    protected String buildUkPkPrefixKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected JedisPool getJedisPool() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Integer getIdByUkDriectFromDb(ActivityVoteDetailInfo t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected LoadDao<ActivityVoteDetailInfo> getLoadDao() {
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
    public CommonDao<ActivityVoteDetailInfo> getCommonDao() {
        // TODO Auto-generated method stub
        return null;
    }

}
