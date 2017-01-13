// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.UserReadNoticeRecordDao;
import com.jmcxclub.dream.family.dto.UserReadNoticeRecord;
import com.jmcxclub.dream.family.service.UserReadNoticeRecordService;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
@Service("userReadNoticeRecordService")
public class UserReadNoticeRecordServiceImpl extends UserReadNoticeRecordService {
    @Autowired
    private UserReadNoticeRecordDao userReadNoticeRecordDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "user:read:notice:record";

    @Override
    protected ShardedJedisPool getSharedJedisPool() {
        return shardedJedisPool;
    }

    @Override
    protected String buildDataInfoKey(int id) {
        return RedisCacheUtils.buildKey(infoKey, id);
    }

    @Override
    public CommonDao<UserReadNoticeRecord> getCommonDao() {
        return userReadNoticeRecordDao;
    }

    @Override
    public void modifyReadTime(int userId) throws ServiceException {
        try {
            userReadNoticeRecordDao.updateReadTime(userId);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        UserReadNoticeRecord g = new UserReadNoticeRecord();
        g.setId(userId);
        afterModifyData(g);
    }

}
