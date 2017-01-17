// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.JedisPool;

import com.dreambox.core.DbStatus;
import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.model.ListWrapResp;
import com.dreambox.web.utils.CollectionUtils;

/**
 * 
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public abstract class AbstractSortedListCacheService<T extends DbStatus> extends AbsCommonCacheDataLoadService<T> {
    public ListWrapResp<T> listInfo(StartSizeCacheFilter filter) {
        long total = count(filter);
        List<Integer> ids = getIds(filter);
        getLogger().info("Result info ids:" + ids);
        if (ids.isEmpty()) {
            return new ListWrapResp<T>(total, new ArrayList<T>(0), false, filter.getStart());
        }
        List<T> values = getData(ids);
        return formatRespValues(values, total, filter.getStart(), filter.getSize());
    }

    public ListWrapResp<Integer> listId(StartSizeCacheFilter filter) {
        long total = count(filter);
        List<Integer> ids = getIds(filter);
        boolean more = filter.getStart() + filter.getSize() > total;
        int next = filter.getStart() + ids.size();
        return new ListWrapResp<Integer>(total, ids, more, next);
    }

    /**
     * 所有实现此方法的类都需要保证返回值与values中对应值的顺序一致
     * 
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <N> List<N> formatCacheValues(List<T> values) {
        return (List<N>) values;
    }

    public long count(StartSizeCacheFilter filter) {
        String key = buildSortedSetKey(filter);
        if (StringUtils.isEmpty(key)) {
            return 0l;
        }
        return RedisCacheUtils.zcard(key, getJedisPool());
    }

    protected abstract String buildSortedSetKey(StartSizeCacheFilter filter);

    protected List<String> buildSortedSetKeys(StartSizeCacheFilter filter) {
        List<String> keys = new ArrayList<String>();
        if (StringUtils.isNotEmpty(buildSortedSetKey(filter))) {
            keys.add(buildSortedSetKey(filter));
        }
        return keys;
    }

    protected List<Integer> getIds(StartSizeCacheFilter filter) {
        String key = buildSortedSetKey(filter);
        if (StringUtils.isEmpty(key)) {
            return Collections.emptyList();
        }
        if (filter.isReverse()) {
            return RedisCacheUtils.zrevrange(key, filter.getStart(), filter.getStart() + filter.getSize() - 1,
                    getJedisPool());
        } else {
            return RedisCacheUtils.zrangeIds(key, filter.getStart(), filter.getStart() + filter.getSize() - 1,
                    getJedisPool());
        }
    }

    protected ListWrapResp<T> formatRespValues(List<T> values, long total, int start, int size) {
        return new ListWrapResp<T>(total, (List<T>) values, total > start + size, start + values.size());
    }

    protected abstract JedisPool getJedisPool();

    @Override
    protected void putToCacheDb(List<T> values, long versionCode) {
        Map<String, Map<String, Double>> addMemberScores = new HashMap<String, Map<String, Double>>();
        Map<String, List<String>> removeMembers = new HashMap<String, List<String>>();
        List<String> removeInfoKeys = new ArrayList<String>();
        for (T value : values) {
            StartSizeCacheFilter filter = buildCacheFilter(value);
            List<String> idzSetKeys = buildSortedSetKeys(filter);
            if (CollectionUtils.emptyOrNull(idzSetKeys)) {
                continue;
            }
            String infoKey = buildDataInfoKey(getIdFromG(value));
            if (illegalValue(value)) {
                List<String> members = buildSortedSetMembers(value);
                for (int i = 0; i < idzSetKeys.size(); i++) {
                    String idzSetKey = idzSetKeys.get(i);
                    if (StringUtils.isEmpty(idzSetKey)) {
                        continue;
                    }
                    String member = members.get(i);
                    if (StringUtils.isEmpty(member)) {
                        continue;
                    }
                    try {
                        CollectionUtils.mapAddList(idzSetKey, removeMembers, member);
                    } catch (IllegalAccessException e) {
                    }
                }
                if (!StringUtils.isEmpty(infoKey)) {
                    removeInfoKeys.add(infoKey);
                }
            } else {
                List<String> members = buildSortedSetMembers(value);
                List<Double> scores = buildSortedSetScores(value);
                for (int i = 0; i < idzSetKeys.size(); i++) {
                    String idzSetKey = idzSetKeys.get(i);
                    if (StringUtils.isEmpty(idzSetKey)) {
                        continue;
                    }
                    String member = members.get(i);
                    if (StringUtils.isEmpty(member)) {
                        continue;
                    }
                    Double score = scores.get(i);
                    if (score == null) {
                        continue;
                    }
                    Map<String, Double> innerMemberScore = new HashMap<String, Double>();
                    innerMemberScore.put(member, score);
                    try {
                        CollectionUtils.mapAddList(idzSetKey, addMemberScores, innerMemberScore);
                    } catch (IllegalAccessException e) {
                    }
                }
            }
        }
        RedisCacheUtils.zadd(addMemberScores, getJedisPool());
        RedisCacheUtils.zrem(removeMembers, getJedisPool());

        RedisCacheUtils.asyncDel(null, removeInfoKeys, getSharedJedisPool());

        additionalPutToCacheDB(values, versionCode);
    }

    /**
     * 如果实现有更多需要在缓存重载中进行操作，可以实现此方法
     * 
     * @param values
     * @param versionCode
     */
    protected void additionalPutToCacheDB(List<T> values, long versionCode) {
    }

    protected boolean illegalValue(T value) {
        return value.isDel();
    }

    protected abstract StartSizeCacheFilter buildCacheFilter(T value);

    protected String buildSortedSetMember(T t) {
        return String.valueOf(getIdFromG(t));
    }

    protected List<String> buildSortedSetMembers(T t) {
        List<String> members = new ArrayList<String>();
        members.add(buildSortedSetMember(t));
        return members;
    }

    protected double buildSortedSetScore(T t) {
        return Long.valueOf(t.getUpdateTime().getTime()).doubleValue();
    }

    protected List<Double> buildSortedSetScores(T t) {
        List<Double> scores = new ArrayList<Double>();
        scores.add(buildSortedSetScore(t));
        return scores;
    }
}
