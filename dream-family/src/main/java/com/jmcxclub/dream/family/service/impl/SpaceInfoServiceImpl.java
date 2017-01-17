// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dao.SpaceInfoDao;
import com.jmcxclub.dream.family.dto.SpaceInfo;
import com.jmcxclub.dream.family.service.SpaceInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Service("spaceInfoService")
public class SpaceInfoServiceImpl extends SpaceInfoService {
    @Autowired
    private SpaceInfoDao spaceInfoDao;
    @Autowired
    @Resource(name = "dream-family-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoPrefixKey = "space:info";

    @Override
    public void addData(SpaceInfo g) throws ServiceException {
        int id;
        try {
            id = spaceInfoDao.insertReturnId(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        g.setId(id);
    }

    @Override
    public void modifyInfo(int id, String name, Date bornDate, String info) throws ServiceException {
        SpaceInfo g = getData(id);
        if (g == null) {
            throw ServiceException.getParameterException("Invaild space id.");
        }
        if (StringUtils.equalsIgnoreCase(g.getName(), name) && StringUtils.equalsIgnoreCase(g.getInfo(), info)
                && org.apache.commons.lang3.time.DateUtils.isSameDay(bornDate, g.getBornDate())) {
            return;
        }
        if (!StringUtils.isEmpty(name)) {
            g.setName(name);
        }
        if (bornDate != null) {
            g.setBornDate(bornDate);
        }
        if (!StringUtils.isEmpty(info)) {
            g.setInfo(info);
        }
        try {
            spaceInfoDao.updateNameAndBornDateAndInfo(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        afterModifyData(g);
    }

    @Override
    public void modifyIcon(int id, String icon) throws ServiceException {
        SpaceInfo g = getData(id);
        if (g == null) {
            throw ServiceException.getParameterException("Invaild space id.");
        }
        if (StringUtils.equalsIgnoreCase(g.getIcon(), icon)) {
            return;
        }
        g.setIcon(icon);
        try {
            spaceInfoDao.updateIcon(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        afterModifyData(g);
    }

    @Override
    public void modifyCover(int id, String cover) throws ServiceException {
        SpaceInfo g = getData(id);
        if (g == null) {
            throw ServiceException.getParameterException("Invaild space id.");
        }
        if (StringUtils.equalsIgnoreCase(g.getCover(), cover)) {
            return;
        }
        g.setCover(cover);
        try {
            spaceInfoDao.updateCover(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        afterModifyData(g);
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
    public CommonDao<SpaceInfo> getCommonDao() {
        return spaceInfoDao;
    }

}
