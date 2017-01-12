// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import org.springframework.web.multipart.MultipartFile;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.model.ActivityInfoResp;
import com.jmcxclub.dream.family.model.ActivityVoteInfoResp;
import com.jmcxclub.dream.family.model.ActivityWorksResp;
import com.jmcxclub.dream.family.model.DiscoveryListResp;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public interface DiscoveryService {

    ApiRespWrapper<ListWrapResp<DiscoveryListResp>> listDiscovery(String openId, Integer start, Integer size)
            throws ServiceException;

    ApiRespWrapper<ActivityInfoResp> getActivity(String openId, int activityId) throws ServiceException;

    ApiRespWrapper<Boolean> applyActivity(int userId, int activityId, int feedId) throws ServiceException;

    ApiRespWrapper<Boolean> voteActivity(int userId, int activityId, int worksId, String ip) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ActivityVoteInfoResp>> listActivityResult(String openId, int activityId)
            throws ServiceException;

    ApiRespWrapper<Boolean> applyActivity(int userId, int activityId, MultipartFile image, String solgan, String desc)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<ActivityWorksResp>> listActivityWorks(UserInfo userInfo, Integer activityId, String findKey,
            Integer start, Integer size) throws ServiceException;

}
