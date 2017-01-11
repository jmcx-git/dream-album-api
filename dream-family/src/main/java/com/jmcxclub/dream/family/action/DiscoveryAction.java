// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.core.utils.ParameterUtils;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.model.OccupantFootprintResp;
import com.jmcxclub.dream.family.model.SpaceFeedListResp;
import com.jmcxclub.dream.family.model.SpaceInfoResp;
import com.jmcxclub.dream.family.model.SpaceListResp;
import com.jmcxclub.dream.family.service.ImgService;
import com.jmcxclub.dream.family.service.SpaceService;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
@Controller
@RequestMapping("/discovery/*")
public class DiscoveryAction extends IosBaseAction {
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<SpaceListResp>> listSpace(String openId, Integer start, Integer size,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<SpaceListResp>>(-1, "未知的用户账号", null);
        }
        start = ParameterUtils.formatStart(start);
        return spaceService.listSpace(openId, start, size);
    }

    @RequestMapping("/activity/detail.json")
    @ResponseBody
    public ApiRespWrapper<SpaceInfoResp> detailSpace(String openId, int spaceId, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<SpaceInfoResp>(-1, "未知的用户账号", null);
        }
        return spaceService.getSpaceInfo(openId, spaceId);
    }

    @RequestMapping("/activity/apply.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> apply(String openId, int spaceId, Integer start,
            Integer size, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(-1, "未知的用户账号", null);
        }
        start = ParameterUtils.formatStart(start);
        return spaceService.listSpaceFeed(openId, spaceId, start, size);
    }

    @RequestMapping("/activity/vote.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> listSpaceFeed(String openId, int spaceId, Integer start,
            Integer size, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(-1, "未知的用户账号", null);
        }
        start = ParameterUtils.formatStart(start);
        return spaceService.listSpaceFeed(openId, spaceId, start, size);
    }

    @RequestMapping("/activity/result.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<OccupantFootprintResp>> listSpaceOccupant(String openId, int spaceId,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<OccupantFootprintResp>>(-1, "未知的用户账号", null);
        }
        return spaceService.listSpaceOccupantFootprint(openId, spaceId);
    }
}
