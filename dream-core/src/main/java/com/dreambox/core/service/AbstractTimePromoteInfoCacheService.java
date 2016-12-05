// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.JedisPool;

import com.dreambox.core.cache.CacheFilter;
import com.dreambox.core.dto.TimePromoteInfo;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.model.ListWrapResp;
import com.dreambox.web.utils.CollectionUtils;

/**
 * 
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public abstract class AbstractTimePromoteInfoCacheService<T extends TimePromoteInfo> extends
        AbsCommonCacheDataLoadService<T> {
    public <M, N> ListWrapResp<M> list(CacheFilter filter, int start, int size) {
        List<Integer> currentPromoteIds = getCurrentPromoteIds(filter);
        if (currentPromoteIds.isEmpty()) {
            return new ListWrapResp<M>(0, new ArrayList<M>(0), false, 0);
        }
        List<T> values = getData(currentPromoteIds);
        Collections.sort(values);
        List<N> comparas = formatCacheValues(values);
        comparas = uniq(comparas);
        long total = comparas.size();
        if (start >= comparas.size()) {
            return new ListWrapResp<M>(total, new ArrayList<M>(0), false, 0);
        }
        int subSize = start + size > comparas.size() ? comparas.size() : start + size;
        comparas = comparas.subList(start, subSize);
        return formatRespValues(comparas, total, start, size);
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

    protected <M> List<M> uniq(List<M> values) {
        return new ArrayList<M>(new LinkedHashSet<M>(values));
    }

    protected List<Integer> getCurrentPromoteIds(CacheFilter filter) {
        String startKey = buildStartKey(filter);
        if (StringUtils.isEmpty(startKey)) {
            return Collections.emptyList();
        }
        String endKey = buildEndKey(filter);
        if (StringUtils.isEmpty(endKey)) {
            return Collections.emptyList();
        }
        double currentScore = currentScore();
        Set<String> startIds = RedisCacheUtils.zrangebyscore(startKey, 0, currentScore, getJedisPool());
        Set<String> endIds = RedisCacheUtils.zrangebyscore(endKey, currentScore, Double.MAX_VALUE, getJedisPool());
        endIds.retainAll(startIds);
        return CollectionUtils.collectionsToIntList(endIds, true);
    }

    protected double currentScore() {
        return System.currentTimeMillis();
    }

    protected abstract String buildStartKey(CacheFilter filter);

    protected abstract String buildEndKey(CacheFilter filter);

    @SuppressWarnings("unchecked")
    protected <M, N> ListWrapResp<M> formatRespValues(List<N> values, long total, int start, int size) {
        return new ListWrapResp<M>(total, (List<M>) values, total > start + size, start + values.size());
    }

    protected abstract JedisPool getJedisPool();

    protected abstract List<String> buildInfoKeys(Set<String> currentPromoteIds);
}
