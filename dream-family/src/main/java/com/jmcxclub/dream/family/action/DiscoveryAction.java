// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.core.utils.ParameterUtils;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.logger.LoggingFilter;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.dto.ActivityWorksInfoEnum;
import com.jmcxclub.dream.family.model.ActivityInfoResp;
import com.jmcxclub.dream.family.model.ActivityWorksResp;
import com.jmcxclub.dream.family.model.DiscoveryListResp;
import com.jmcxclub.dream.family.service.DiscoveryService;
import com.jmcxclub.dream.family.service.ImgService;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

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
    @Autowired
    private ImgService imgService;

    private UserInfo getUserInfo(String openId) {
        UserInfo g = new UserInfo();
        g.setOpenId(openId);
        return userInfoService.getInfoByUk(g);
    }

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
    @RequestMapping("/activity/info.json")
    @ResponseBody
    public ApiRespWrapper<ActivityInfoResp> getActivityInfo(String openId, Integer id, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ActivityInfoResp>(-1, "未知的用户账号", null);
        }
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<ActivityInfoResp>(-1, "未找到对应用户账号");
        }
        return discoveryService.getActivity(userInfo.getId(), id);
    }

    /**
     * 参赛作品列表
     * 
     * @param openId
     * @param id
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/activity/works/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<ActivityWorksResp>> listActivityWorks(String openId, Integer id, String findKey,
            Integer start, Integer size, String version, Integer voteWorksId) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<ActivityWorksResp>>(-1, "未知的用户账号", null);
        }
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<ListWrapResp<ActivityWorksResp>>(-1, "未找到对应用户账号");
        }
        start = ParameterUtils.formatStart(start);
        size = ParameterUtils.formatStart(size);
        return discoveryService.listActivityWorks(userInfo, id, findKey, voteWorksId, start, size);
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
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<Boolean>(-1, "未找到对应用户账号");
        }
        return discoveryService.applyActivity(userInfo.getId(), id, feedId);
    }

    /**
     * 参与活动,调用成功后客户端
     * 
     * @param openId
     * @param id
     * @param feedId
     * @param version
     * @param type see {@link ActivityWorksInfoEnum}
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = "/activity/apply.json", method = RequestMethod.POST)
    @ResponseBody
    public ApiRespWrapper<Boolean> applyActivity(String openId, Integer id,
            @RequestParam(required = false) MultipartFile image, String solgan, String desc, Integer type,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<Boolean>(-1, "未找到对应用户账号");
        }
        solgan = ContentDescUtils.decode(solgan);
        desc = ContentDescUtils.decode(desc);
        if (type == null || type.intValue() == ActivityWorksInfoEnum.NORMAL.getType()) {
            return discoveryService.applyActivity(userInfo.getId(), id, image, solgan, desc);
        } else {
            return new ApiRespWrapper<Boolean>(-1, "不支持的参赛作品类型", false);
        }
        // return null;
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
    public ApiRespWrapper<Boolean> voteActivity(String openId, Integer id, Integer worksId, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<Boolean>(-1, "未找到对应用户账号");
        }
        Map<String, String> httpParameters = LoggingFilter.threadLocalMap.get();
        String ip = httpParameters != null ? httpParameters.get("ip") : "";
        return discoveryService.voteActivity(userInfo.getId(), id, worksId, ip);
    }
}
