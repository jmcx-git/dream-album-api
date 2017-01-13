// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.jmcxclub.dream.family.dao.SystemNoticeInfoDao;
import com.jmcxclub.dream.family.dto.SystemNoticeInfo;
import com.jmcxclub.dream.family.service.SystemNoticeInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
@Service("systemNoticeInfoService")
public class SystemNoticeInfoServiceImpl extends SystemNoticeInfoService {
    @Autowired
    private SystemNoticeInfoDao systemNoticeInfoDao;
    private String infoKey = "system:notice:info:";
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;

    @Override
    protected ShardedJedisPool getSharedJedisPool() {
        return shardedJedisPool;
    }

    @Override
    protected String buildDataInfoKey(int id) {
        return RedisCacheUtils.buildKey(infoKey, id);
    }

    @Override
    public CommonDao<SystemNoticeInfo> getCommonDao() {
        return systemNoticeInfoDao;
    }

}
