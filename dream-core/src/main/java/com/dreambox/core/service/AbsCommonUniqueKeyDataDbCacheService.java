// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.JedisPool;

import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;

/**
 * @author luofei@appchina.com create date: 2016年4月22日
 *
 */
public abstract class AbsCommonUniqueKeyDataDbCacheService<G extends Serializable> extends AbsCommonDataService<G> {

    protected abstract JedisPool getJedisPool();

    protected boolean canPutToCache(G dbData) {
        return dbData != null;
    }

    public G getData(int id) {
        String key = buildDataInfoKey(id);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        G ret = null;
        ret = RedisCacheUtils.getObject(key, getJedisPool());
        if (ret != null) {
            return ret;
        } else {
            ret = getDirectFromDb(id);
            if (canPutToCache(ret)) {
                ret = beforeToCache(ret);
                toCache(ret);
            } else {
                return null;
            }
        }
        return ret;
    }

    protected void toCache(G g) {
        String key = buildDataInfoKey(getId(g));
        if (StringUtils.isEmpty(key)) {
            return;
        }
        if (legalData(g)) {
            RedisCacheUtils.setObject(key, g, getJedisPool());
        } else {
            RedisCacheUtils.del(getJedisPool(), key);
        }
    }

    protected abstract boolean legalData(G g);

    protected int getId(G g) {
        try {
            return Integer.parseInt(BeanUtils.getProperty(g, "id"));
        } catch (Exception e) {
            throw ServiceException.getInternalException("Cannot get id from value:" + g);
        }
    }


    protected abstract String buildDataInfoKey(int id);

    protected G beforeToCache(G g) {
        int id = getId(g);
        if (id <= 0) {
            throw ServiceException.getInternalException("Cannot get id from value:" + g);
        }
        return getDirectFromDb(id);
    }

    @Override
    protected void afterModifyData(List<G> gg) {
        for (G g : gg) {
            beforeToCache(g);
            toCache(g);
        }
    }
}
