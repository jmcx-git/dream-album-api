// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import redis.clients.jedis.JedisPool;

import com.dreambox.core.dao.CommonDao;



/**
 * 提供redis zset相关服务
 * 
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public interface CommonZsetHelper {
    public int nextId(List<?> datas);

    public abstract String buildZsetKey(Object value);

    public abstract boolean isOnline(Object value);

    public abstract Double getMemberScore(Object value);

    public abstract String getMemberKey(Object value);

    public abstract JedisPool getJedisPool();

    public abstract CommonDao<?> getCommonDao();

    public abstract Logger getLogger();

    public boolean isStop();

    public String[] getInfoKeys(Object value);

    public Serializable convert(Object value);

}
