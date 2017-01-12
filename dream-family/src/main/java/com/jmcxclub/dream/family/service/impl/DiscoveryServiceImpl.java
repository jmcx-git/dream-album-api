// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.dreambox.web.utils.CollectionUtils;
import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.model.ActivityInfoResp;
import com.jmcxclub.dream.family.model.ActivityVoteInfoResp;
import com.jmcxclub.dream.family.model.DiscoveryListResp;
import com.jmcxclub.dream.family.service.ActivityInfoService;
import com.jmcxclub.dream.family.service.ActivityPrizeInfoService;
import com.jmcxclub.dream.family.service.ActivityVoteDetailInfoService;
import com.jmcxclub.dream.family.service.ActivityVoteStatInfoService.ActivityVoteStatInfoSortedListCacheFilter;
import com.jmcxclub.dream.family.service.ActivityWorksInfoService;
import com.jmcxclub.dream.family.service.ActivityVoteStatInfoService;
import com.jmcxclub.dream.family.service.DiscoveryService;

/**
 * 发现tab页数据服务类
 * 
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("discoveryService")
public class DiscoveryServiceImpl implements DiscoveryService {
    @Autowired
    private ActivityInfoService activityInfoService;
    // @Autowired
    private ActivityPrizeInfoService activityPrizeInfoService;
    @Autowired
    private ActivityWorksInfoService activityWorksInfoService;
    @Autowired
    private ActivityVoteStatInfoService activityWorksStatInfoService;
    @Autowired
    private ActivityVoteDetailInfoService activityVoteDetailInfoService;

    @Override
    public ApiRespWrapper<ListWrapResp<DiscoveryListResp>> listDiscovery(String openId, Integer start, Integer size)
            throws ServiceException {
        StartSizeCacheFilter filter = new StartSizeCacheFilter();
        filter.setStart(start);
        filter.setSize(size);
        ListWrapResp<ActivityInfo> infos = activityInfoService.listInfo(filter);
        if (infos == null || CollectionUtils.emptyOrNull(infos.getResultList())) {
            return new ApiRespWrapper<ListWrapResp<DiscoveryListResp>>(new ListWrapResp<DiscoveryListResp>(
                    new ArrayList<DiscoveryListResp>()));
        }
        List<DiscoveryListResp> datas = new ArrayList<DiscoveryListResp>();
        ActivityVoteStatInfoSortedListCacheFilter statCacheFilter = new ActivityVoteStatInfoSortedListCacheFilter();
        for (ActivityInfo info : infos.getResultList()) {
            long participates = activityWorksStatInfoService.count(statCacheFilter);
            datas.add(new DiscoveryListResp(info, participates));
        }
        return new ApiRespWrapper<ListWrapResp<DiscoveryListResp>>(new ListWrapResp<DiscoveryListResp>(
                infos.getTotalCount(), datas, infos.isMore(), infos.getNext()));
    }

    @Override
    public ApiRespWrapper<ActivityInfoResp> getActivity(String openId, int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<Boolean> applyActivity(String openId, int id, int feedId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<Boolean> voteActivity(String openId, int id, int chooseId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ActivityVoteInfoResp>> listActivityResult(String openId, int id) {
        // TODO Auto-generated method stub
        return null;
    }

}
