// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.JedisPool;

import com.dreambox.core.dto.DbKey.PRIMARY_KEY;
import com.dreambox.core.dto.DbKey.UNIQUE_KEY;
import com.dreambox.core.dto.StatusSerializable;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.utils.CollectionUtils;

/**
 * DB UNIQUE KEY REFLECT KEY
 * 
 * the key is the unique key in db and the value is the primary key in db
 * 
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public abstract class AbsCommonCacheDbUkPkLoadService<T extends StatusSerializable> extends
        AbsCommonCacheDataLoadService<T> {
    public int getIdByUk(T g) {
        String key = buildUkReflectPkKey(g);
        String result = RedisCacheUtils.get(key, getJedisPool());
        if (StringUtils.isEmpty(result)) {
            Integer id = getIdByUkDriectFromDb(g);
            if (id == null) {
                return -1;
            }
            RedisCacheUtils.set(key, id.toString(), getJedisPool());
            return id;
        }
        return Integer.parseInt(result);
    }

    public T getInfoByUk(T g) {
        int id = getIdByUk(g);
        T t = getData(id);
        t = formatCacheData(t);
        return t;
    }

    protected T formatCacheData(T t) {
        return t;
    }

    @Override
    protected void putToCacheDb(List<T> tt, long versionCode) {
        Map<String, T> infoCaches = new HashMap<String, T>();
        List<String> removeInfoKeys = new ArrayList<String>();
        Map<String, String> keyValueMap = new HashMap<String, String>();
        for (T t : tt) {
            String infoKey = buildDataInfoKey(getIdFromG(t));
            if (!StringUtils.isEmpty(infoKey)) {
                if (t.isOk()) {
                    infoCaches.put(infoKey, t);
                } else {
                    removeInfoKeys.add(infoKey);
                }
            }
            String key = buildUkReflectPkKey(t);
            if (StringUtils.isEmpty(key)) {
                continue;
            }
            String value = buildPkValue(t);
            if (StringUtils.isEmpty(value)) {
                value = String.valueOf(getIdFromG(t));
            }
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            keyValueMap.put(key, value);
        }

        RedisCacheUtils.mset(keyValueMap, getJedisPool());
        RedisCacheUtils.setMultiObjects(infoCaches, getSharedJedisPool());
        RedisCacheUtils.asyncDel(null, removeInfoKeys, getSharedJedisPool());
    }

    protected String buildUkReflectPkKey(T t) {
        List<String> uniqueKeyFieldValueList = new ArrayList<String>();
        Class<?> clazz = t.getClass();
        Class<?> superClazz = clazz.getSuperclass();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e1) {
            return null;
        }
        BeanInfo superBeanInfo = null;
        try {
            superBeanInfo = Introspector.getBeanInfo(superClazz);
        } catch (IntrospectionException e1) {
        }
        PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
        if (props == null) {
            return null;
        }
        PropertyDescriptor[] superProps = superBeanInfo == null ? null : superBeanInfo.getPropertyDescriptors();
        List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>(Arrays.asList(props));
        if (CollectionUtils.notEmptyAndNull(superProps)) {
            propList.removeAll(Arrays.asList(superProps));
        }
        for (PropertyDescriptor prop : propList) {
            String name = prop.getName();
            try {
                Field f = clazz.getDeclaredField(name);
                f.setAccessible(true);
                if (f.isAnnotationPresent(UNIQUE_KEY.class)) {
                    uniqueKeyFieldValueList.add(String.valueOf(f.get(t)));
                }
            } catch (Exception e) {
                continue;
            }
        }
        if (CollectionUtils.notEmptyAndNull(uniqueKeyFieldValueList)) {
            return RedisCacheUtils.buildKey(buildUkPkPrefixKey(),
                    CollectionUtils.listToString(uniqueKeyFieldValueList, RedisCacheUtils.KEY_SPLITCHAR));
        }
        return null;
    }

    protected abstract String buildUkPkPrefixKey();

    protected String buildPkValue(T t) {
        Class<?> clazz = t.getClass();
        Class<?> superClazz = clazz.getSuperclass();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e1) {
            return null;
        }
        BeanInfo superBeanInfo = null;
        try {
            superBeanInfo = Introspector.getBeanInfo(superClazz);
        } catch (IntrospectionException e1) {
        }
        PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
        if (props == null) {
            return null;
        }
        PropertyDescriptor[] superProps = superBeanInfo == null ? null : superBeanInfo.getPropertyDescriptors();
        List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>(Arrays.asList(props));
        if (CollectionUtils.notEmptyAndNull(superProps)) {
            propList.removeAll(Arrays.asList(superProps));
        }
        for (PropertyDescriptor prop : propList) {
            String name = prop.getName();
            try {
                Field f = clazz.getDeclaredField(name);
                f.setAccessible(true);
                if (f.isAnnotationPresent(PRIMARY_KEY.class)) {
                    return String.valueOf(f.get(t));
                }
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    protected abstract JedisPool getJedisPool();

    protected abstract Integer getIdByUkDriectFromDb(T t);
}
