// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.asm.commons.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/space/discovery/*")
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
     * 如果发现列表中某项数据的type为0则在进入详情时调用进入活动详情
     * 
     * @param openId
     * @param id
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/activity/detail.json")
    @ResponseBody
    public ApiRespWrapper<ActivityInfoResp> detailSpace(String openId, Integer id, String version)
            throws ServiceException {
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
    @RequestMapping(value = "/activity/apply.json", method = RequestMethod.GET)
    @ResponseBody
    public ApiRespWrapper<Boolean> joinActivity(String openId, Integer id, Integer feedId, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        return discoveryService.applyActivity(openId, id, feedId);
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
    @RequestMapping(value = "/activity/apply.json", method = RequestMethod.POST)
    @ResponseBody
    public ApiRespWrapper<Boolean> applyActivity(String openId, Integer id,
            @RequestParam(required = false) MultipartFile image, String solgan, String desc, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        // return discoveryService.applyActivity(openId, id, feedId);
        return null;
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
    public ApiRespWrapper<Boolean> voteActivity(String openId, Integer id, Integer chooseId, String version)
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
    public ApiRespWrapper<ListWrapResp<ActivityVoteInfoResp>> listActivityResult(String openId, Integer id,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<ActivityVoteInfoResp>>(-1, "未知的用户账号", null);
        }
        return discoveryService.listActivityResult(openId, id);
    }
}
