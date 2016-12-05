// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dreambox.core.StatusType;
import com.dreambox.core.dto.EnumHtmlSelectType;
import com.dreambox.web.utils.CollectionUtils;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class EnumMapUtils {
    public static final <E extends Enum<E>> Map<String, String> enumToMap(Enum<E>[] objects, boolean all) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        if (all) {
            map.put(String.valueOf(StatusType.STATUS_ALL.getStatus()), StatusType.STATUS_ALL.getDesc());
        }
        boolean notFoundKeyValueAnnotation = true;
        for (Object object : objects) {
            Class<?> clazz = object.getClass();
            Class<?> superClazz = clazz.getSuperclass();
            BeanInfo beanInfo;
            try {
                beanInfo = Introspector.getBeanInfo(clazz);
            } catch (IntrospectionException e1) {
                continue;
            }
            BeanInfo superBeanInfo = null;
            try {
                superBeanInfo = Introspector.getBeanInfo(superClazz);
            } catch (IntrospectionException e1) {
            }
            PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
            if (props == null) {
                continue;
            }
            PropertyDescriptor[] superProps = superBeanInfo == null ? null : superBeanInfo.getPropertyDescriptors();
            List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>(Arrays.asList(props));
            if (CollectionUtils.notEmptyAndNull(superProps)) {
                propList.removeAll(Arrays.asList(superProps));
            }
            String key = "";
            String value = "";
            for (PropertyDescriptor prop : propList) {
                String name = prop.getName();
                try {
                    Field f = clazz.getDeclaredField(name);
                    f.setAccessible(true);
                    if (f.isAnnotationPresent(EnumHtmlSelectType.KEY.class)) {
                        key = String.valueOf(f.get(object));
                    } else if (f.isAnnotationPresent(EnumHtmlSelectType.VALUE.class)) {
                        if (StringUtils.isEmpty(value)) {
                            value = String.valueOf(f.get(object));
                        } else {
                            value = value + "---" + String.valueOf(f.get(object));
                        }
                    }
                } catch (Exception e) {
                }
            }
            if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                notFoundKeyValueAnnotation = false;
                map.put(key, value);
            }
        }
        if (notFoundKeyValueAnnotation) {
            map.clear();
            for (Enum<E> object : objects) {
                map.put(object.name(), object.name());
            }
        }
        return map;
    }

    public static final <E extends Enum<E>> Map<String, String> enumToMap(Enum<E>[] objects) {
        return enumToMap(objects, true);
    }
}
