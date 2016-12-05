// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.dreambox.core.dao.LoadDao;
import com.dreambox.web.exception.ServiceException;

/**
 * @author luofei@refanqie.com (Your Name Here)
 */
public abstract class AbsLoadService<T> implements LoadService, Comparable<AbsLoadService<T>> {
    public static final List<AbsLoadService<?>> LOAD_SERVICES = new CopyOnWriteArrayList<AbsLoadService<?>>();

    public AbsLoadService() {
        for (AbsLoadService<?> service : LOAD_SERVICES) {
            if (service.getClass().equals(this.getClass())) {
                return;
            }
        }
        LOAD_SERVICES.add(this);
    }


    @Override
    public void cacheInitLoad() throws ServiceException {
        deltaCacheInitLoad(0);
    }

    protected int getSize() {
        return 1000;
    }

    protected long getEnableCacheVersionCode() {
        return 0;
    }

    protected long generateCacheVersionCode() {
        return 0;
    }

    protected void saveEnableCacheVersionCode(long versionCode) {
    }

    protected void expirePreVersionCache(long cachePreEnableVersionCode) {
    }

    protected abstract Logger getLogger();

    protected int getNextId(List<T> values) throws Exception {
        T t = values.get(values.size() - 1);
        try {
            return (int) PropertyUtils.getProperty(t, "id");
        } catch (Exception e) {
            throw e;
        }
    }

    protected abstract void putToCacheDb(List<T> values, long versionCode);

    protected void putToCacheDb(List<T> values) {
        putToCacheDb(values, 0);
    }

    protected abstract LoadDao<T> getLoadDao();

    protected abstract boolean isStop();

    /**
     * 值越小，越第一时间进行load
     *
     * @return
     */
    protected int getPriority() {
        Field[] fields = getClass().getFields();
        for (Field field : fields) {
            Type clazz = field.getType();
            if (clazz instanceof AbsLoadService) {
                // TODO
            }
        }
        return 0;
    }

    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public int compareTo(AbsLoadService<T> o) {
        return this.getPriority() - o.getPriority();
    }


    @Override
    public void deltaCacheInitLoad(int startId) throws ServiceException {
        long versionCode = generateCacheVersionCode();
        int size = getSize();
        do {
            List<T> values = null;
            try {
                getLogger().info("Load " + getName() + " data from Id:" + startId + ", size:" + size);
                values = this.getLoadDao().queryList(startId, size);
            } catch (SQLException e) {
                throw ServiceException.getSQLException(e.getMessage());
            }
            putToCacheDb(values, versionCode);
            if (values.size() < size) {
                break;
            }
            try {
                startId = getNextId(values);
            } catch (Exception e) {
                throw ServiceException.getInternalException(e.getMessage());
            }
        } while (!isStop());
        long cachePreEnableVersionCode = getEnableCacheVersionCode();
        saveEnableCacheVersionCode(versionCode);
        expirePreVersionCache(cachePreEnableVersionCode);
        getLogger().info("Finish load " + getName() + " data.");
    }


    @Override
    public void deltaCacheInitLoad(List<Integer> deltaIds) throws ServiceException {
        throw ServiceException.getInternalException("Unsupport method.");
    }
}
