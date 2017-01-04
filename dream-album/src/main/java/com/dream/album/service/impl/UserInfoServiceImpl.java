package com.dream.album.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dream.album.dao.UserInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;

@Service("userInfoService")
public class UserInfoServiceImpl extends UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String userInfoKey = "user:info";
    private String openIdReflectIdUkKey = "uk:openid:id";

    @Override
    public CommonDao<UserInfo> getCommonDao() {
        return userInfoDao;
    }

    @Override
    public int addDataAndReturnId(UserInfo g) throws ServiceException {
        try {
            return userInfoDao.insertAndReturnId(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public void modifyUserInfo(UserInfo g) throws ServiceException {
        try {
            userInfoDao.update(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        RedisCacheUtils.del(buildDataInfoKey(g.getId()), shardedJedisPool);
    }

    @Override
    protected String buildUkPkPrefixKey() {
        return openIdReflectIdUkKey;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected Integer getIdByUkDriectFromDb(UserInfo t) {
        try {
            return userInfoDao.queryIdByUk(t.getOpenId());
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    protected LoadDao<UserInfo> getLoadDao() {
        return userInfoDao;
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
        return RedisCacheUtils.buildKey(userInfoKey, id);
    }


}
