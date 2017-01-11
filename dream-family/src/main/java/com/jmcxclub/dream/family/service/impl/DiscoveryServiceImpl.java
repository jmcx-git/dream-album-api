// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import org.springframework.stereotype.Service;

import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.model.ActivityInfoResp;
import com.jmcxclub.dream.family.model.ActivityVoteInfoResp;
import com.jmcxclub.dream.family.model.DiscoveryListResp;
import com.jmcxclub.dream.family.service.DiscoveryService;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("discoveryService")
public class DiscoveryServiceImpl implements DiscoveryService {

    @Override
    public ApiRespWrapper<ListWrapResp<DiscoveryListResp>> listDiscovery(String openId, Integer start, Integer size)
            throws ServiceException {
        // TODO Auto-generated method stub
        return null;
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
