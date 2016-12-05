// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.CollectionUtils;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class DefaultCommonZsetService {
    public static void cacheInitLoad(CommonZsetHelper serivce) {
        CommonDao<?> commaonDao = serivce.getCommonDao();
        int startId = 0;
        int size = getSize(commaonDao);
        do {
            List<?> values = null;
            try {
                getLogger(serivce).info(
                        "Load " + serivce.getClass().getName() + " data from Id:" + startId + ", size:" + size);
                values = commaonDao.queryList(startId, size);
            } catch (SQLException e) {
                throw ServiceException.getSQLException(e.getMessage());
            }
            putToCacheDb(values, serivce);
            if (values.size() < size) {
                break;
            }
            try {
                startId = getNextId(values, serivce);
            } catch (Exception e) {
                throw ServiceException.getInternalException(e.getMessage());
            }
        } while (!isStop(serivce));
        getLogger(serivce).info("Finish load " + serivce.getClass().getName() + " data.");
    }

    private static int getSize(CommonDao<?> dao) {
        try {
            long count = dao.count(null);
            return Long.valueOf(count).intValue();
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    protected static int getNextId(List<?> values, CommonZsetHelper serivce) throws Exception {
        try {
            return serivce.nextId(values);
        } catch (Exception e) {
            Object t = values.get(values.size() - 1);
            return (int) PropertyUtils.getProperty(t, "id");
        }
    }

    protected static Logger getLogger(CommonZsetHelper service) {
        return service.getLogger();
    }

    protected static void putToCacheDb(List<?> values, CommonZsetHelper service) {
        Map<String, Serializable> keyValueMap = new HashMap<String, Serializable>();
        List<String> removeInfoKeys = new ArrayList<String>();
        Map<String, Map<String, Double>> addKeyMemberScoreMap = new HashMap<String, Map<String, Double>>();
        Map<String, List<String>> removeKeyMembersMap = new HashMap<String, List<String>>();
        for (Object value : values) {
            String zsetKey = service.buildZsetKey(value);
            String[] infoKeys = service.getInfoKeys(value);
            if (service.isOnline(value)) {
                Map<String, Double> adds = new HashMap<String, Double>();
                adds.put(service.getMemberKey(value), service.getMemberScore(value));
                try {
                    CollectionUtils.mapAddList(zsetKey, addKeyMemberScoreMap, adds);
                } catch (IllegalAccessException e) {
                }
                if (!CollectionUtils.emptyOrNull(infoKeys)) {
                    Serializable serializable = service.convert(value);
                    for (String infoKey : infoKeys) {
                        keyValueMap.put(infoKey, serializable);
                    }
                }
            } else {
                List<String> memberKeys = new ArrayList<String>();
                memberKeys.add(service.getMemberKey(value));
                try {
                    CollectionUtils.mapAddList(zsetKey, removeKeyMembersMap, service.getMemberKey(value));
                } catch (IllegalAccessException e) {
                }
                if (!CollectionUtils.emptyOrNull(infoKeys)) {
                    for (String infoKey : infoKeys) {
                        removeInfoKeys.add(infoKey);
                    }
                }
            }
        }
        RedisCacheUtils.msetObject(keyValueMap, service.getJedisPool());
        RedisCacheUtils.del(removeInfoKeys, service.getJedisPool());
        RedisCacheUtils.zadd(addKeyMemberScoreMap, service.getJedisPool());
        RedisCacheUtils.zrem(removeKeyMembersMap, service.getJedisPool());
    }

    protected static boolean isStop(CommonZsetHelper serivce) {
        return serivce.isStop();
    }
}
