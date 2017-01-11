package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dao.LoadDao;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.UserInfoDao;

@Service("userInfoService")
public class UserInfoServiceImpl extends UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Resource(name = "dream-family-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String userInfoKey = "user:info";
    private String openIdReflectIdUkKey = "uk:openid:id";

    @PostConstruct
    public void initCache() {
        this.cacheInitLoad();
    }

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

    @Override
    public int getIdByUnionIdAndAppId(String unionId, String appId) throws ServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected int getPriority() {
        return 1;
    }


}
