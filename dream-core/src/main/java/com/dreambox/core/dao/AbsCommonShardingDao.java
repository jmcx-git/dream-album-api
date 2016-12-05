// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.dao;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dreambox.core.DbField;
import com.dreambox.core.utils.SQLUtils;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.CollectionUtils;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public abstract class AbsCommonShardingDao<G> implements CommonShardingDao<G> {
    @Override
    public void createTable(String tableSuffix) throws SQLException {
        ShardingDaoHelper.createTable(tableSuffix, this);
    }

    @Override
    public List<G> queryList(String tableSuffix, G g, int start, int size) throws SQLException {
        return ShardingDaoHelper.queryList(tableSuffix, g, start, size, this);
    }

    @Override
    public List<G> queryListByStartId(String tableSuffix, G g, int startId, int size) throws SQLException {
        return ShardingDaoHelper.queryListByStartId(tableSuffix, g, startId, size, this);
    }

    @Override
    public List<G> queryListByEndId(String tableSuffix, G g, int endId, int size) throws SQLException {
        return ShardingDaoHelper.queryListByEndId(tableSuffix, g, endId, size, this);
    }

    @Override
    public G queryObject(String tableSuffix, int id) throws SQLException {
        return ShardingDaoHelper.queryObject(tableSuffix, id, this);
    }

    @Override
    public List<G> queryList(String tableSuffix, List<Integer> ids) throws SQLException {
        return ShardingDaoHelper.queryList(tableSuffix, ids, this);
    }

    @Override
    public void insertOrUpdate(String tableSuffix, G g) throws SQLException {
        ShardingDaoHelper.insertOrUpdate(tableSuffix, g, this);
    }

    @Override
    public void insertOrUpdate(String tableSuffix, List<G> gg) throws SQLException {
        ShardingDaoHelper.insertOrUpdate(tableSuffix, gg, this);
    }

    @Override
    public boolean insertOrIgnore(String tableSuffix, G g) throws SQLException {
        return ShardingDaoHelper.insertOrIgnore(tableSuffix, g, this);
    }

    @Override
    public void updateStatus(String tableSuffix, G g) throws SQLException {
        ShardingDaoHelper.updateStatus(tableSuffix, g, this);
    }

    @Override
    public void updateStatus(String tableSuffix, List<G> gg) throws SQLException {
        ShardingDaoHelper.updateStatus(tableSuffix, gg, this);
    }

    @Override
    public void update(String tableSuffix, G g) throws SQLException {
        ShardingDaoHelper.update(tableSuffix, g, this);
    }

    @Override
    public long count(String tableSuffix, G g) throws SQLException {
        return ShardingDaoHelper.count(tableSuffix, g, this);
    }


    @Override
    public List<G> queryList(String tableSuffix, int startId, int size) throws SQLException {
        return ShardingDaoHelper.queryList(tableSuffix, startId, size, this);
    }

    public String getName() {
        String clazzParameterizedTypeName = getName(this.getClass());
        if (clazzParameterizedTypeName == null) {
            throw ServiceException.getInternalException("Unknown class name for " + this.getName());
        }
        return clazzParameterizedTypeName;
    }

    private static final String getName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            return getName(type);
        } else {
            Class<?> superClass = clazz.getSuperclass();
            return getName(superClass);
        }
    }

    private static String getName(Type type) {
        return ((Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0]).getSimpleName();
    }

    @Override
    public Map<String, Object> buildListPara(String tableSuffix, G g) {
        if (g == null) {
            return SQLUtils.initTableSuffix(tableSuffix);
        }
        List<String> noIgnoreZeroFieldList = buildNoIgnoreZeroFieldList(g);
        return SQLUtils.introspect(tableSuffix, g, true, noIgnoreZeroFieldList);
    }

    private List<String> buildNoIgnoreZeroFieldList(G object) {
        List<String> zeroFieldList = new ArrayList<String>();
        Class<?> clazz = object.getClass();
        Class<?> superClazz = clazz.getSuperclass();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e1) {
            return zeroFieldList;
        }
        BeanInfo superBeanInfo = null;
        try {
            superBeanInfo = Introspector.getBeanInfo(superClazz);
        } catch (IntrospectionException e1) {
        }
        PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
        PropertyDescriptor[] superProps = superBeanInfo == null ? null : superBeanInfo.getPropertyDescriptors();
        if (superProps == null && props == null) {
            return zeroFieldList;
        }
        List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>(Arrays.asList(props));
        List<PropertyDescriptor> superPropList = superProps == null ? new ArrayList<PropertyDescriptor>()
                : new ArrayList<PropertyDescriptor>(Arrays.asList(superProps));
        if (CollectionUtils.notEmptyAndNull(superPropList)) {
            propList.removeAll(superPropList);
        }
        initZeroFieldlList(superPropList, superClazz, zeroFieldList);
        initZeroFieldlList(propList, clazz, zeroFieldList);
        return zeroFieldList;
    }

    private static void initZeroFieldlList(List<PropertyDescriptor> propList, Class<?> clazz, List<String> zeroFieldList) {
        for (PropertyDescriptor prop : propList) {
            String name = prop.getName();
            try {
                Field f = clazz.getDeclaredField(name);
                f.setAccessible(true);
                if (f.isAnnotationPresent(DbField.ZERO_ENABLE.class)) {
                    zeroFieldList.add(f.getName());
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public Map<String, Object> buildCountPara(String tableSuffix, G g) {
        return buildListPara(tableSuffix, g);
    }

    @Override
    public long count(String tableSuffix, G g, String st, String et) throws SQLException {
        if (StringUtils.isEmpty(st) && StringUtils.isEmpty(et)) {
            return count(tableSuffix, g);
        }
        return ShardingDaoHelper.count(tableSuffix, g, st, et, this);
    }

    @Override
    public List<G> queryList(String tableSuffix, G g, String st, String et, int start, int size) throws SQLException {
        if (StringUtils.isEmpty(st) && StringUtils.isEmpty(et)) {
            return queryList(tableSuffix, g, start, size);
        }
        return ShardingDaoHelper.queryList(tableSuffix, g, st, et, start, size, this);
    }

}
