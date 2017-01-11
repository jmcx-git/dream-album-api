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
import com.jmcxclub.dream.family.model.ActivityInfoResp;
import com.jmcxclub.dream.family.model.ActivityVoteInfoResp;
import com.jmcxclub.dream.family.model.DiscoveryListResp;
import com.jmcxclub.dream.family.service.DiscoveryService;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
@Controller
@RequestMapping("/discovery/*")
public class DiscoveryAction extends IosBaseAction {
    @Autowired
    private DiscoveryService discoveryService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 发现列表
     * 
     * @param openId
     * @param start
     * @param size
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<DiscoveryListResp>> listSpace(String openId, Integer start, Integer size,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<DiscoveryListResp>>(-1, "未知的用户账号", null);
        }
        start = ParameterUtils.formatStart(start);
        size = ParameterUtils.formatStart(size);
        return discoveryService.listDiscovery(openId, start, size);
    }

    /**
     * 如果发现列表中某项数据的type为0则调用 进入活动详情
     * 
     * @param openId
     * @param id
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/activity/detail.json")
    @ResponseBody
    public ApiRespWrapper<ActivityInfoResp> detailSpace(String openId, int id, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ActivityInfoResp>(-1, "未知的用户账号", null);
        }
        return discoveryService.getActivity(openId, id);
    }

    /**
     * 参与活动,调用成功后客户端调用activity/result.json
     * 
     * @param openId
     * @param id
     * @param feedId
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/activity/apply.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> applyActivity(String openId, int id, int feedId, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        return discoveryService.applyActivity(openId, id, feedId);
    }

    /**
     * 对某个选项投票
     * 
     * @param openId
     * @param id
     * @param chooseId
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/activity/vote.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> voteActivity(String openId, int id, int chooseId, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        return discoveryService.voteActivity(openId, id, chooseId);
    }

    /**
     * 获取投票结果
     * 
     * @param openId
     * @param id
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/activity/result.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<ActivityVoteInfoResp>> listActivityResult(String openId, int id, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<ActivityVoteInfoResp>>(-1, "未知的用户账号", null);
        }
        return discoveryService.listActivityResult(openId, id);
    }
}
