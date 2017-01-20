// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.core.utils.ParameterUtils;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.model.AboutUsResp;
import com.jmcxclub.dream.family.model.MyInfoResp;
import com.jmcxclub.dream.family.model.NoticeResp;
import com.jmcxclub.dream.family.model.WikiResp;
import com.jmcxclub.dream.family.service.NoticeService;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
@Controller
@RequestMapping("/space/my/*")
public class NoticeAction extends IosBaseAction {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "/info.json")
    @ResponseBody
    public ApiRespWrapper<MyInfoResp> addSpace(String openId, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<MyInfoResp>(-1, "未知的用户账号", null);
        }
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<MyInfoResp>(-1, "未找到此用户");
        }
        boolean notice = noticeService.hasNotice(userInfo.getId());
        return new ApiRespWrapper<MyInfoResp>(new MyInfoResp(userInfo, notice));
    }

    @RequestMapping(value = "/about/us.json")
    @ResponseBody
    public ApiRespWrapper<AboutUsResp> aboutUs(String openId, String version) throws ServiceException {
        return new ApiRespWrapper<AboutUsResp>(new AboutUsResp());
    }

    @RequestMapping(value = "/wiki/detail.json")
    @ResponseBody
    public ApiRespWrapper<WikiResp> detailWiki(String openId, String version, Integer id) throws ServiceException {
        return new ApiRespWrapper<WikiResp>(noticeService.getWiki(id));
    }

    @RequestMapping(value = "/wiki/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<WikiResp>> listWiki(String openId, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<WikiResp>>(-1, "未知的用户账号", null);
        }
        List<WikiResp> wikis = noticeService.listWikis();

        return new ApiRespWrapper<ListWrapResp<WikiResp>>(new ListWrapResp<WikiResp>(wikis));
    }

    /**
     * 对于获取最新，则startId,type不传
     * 
     * 如果是获取更多，则startId,type为上一条对应的id, type
     * 
     * startId,type (同时为空或者同时存在)
     * 
     * @param openId
     * @param startId
     * @param type
     * @param size
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = "/notice/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<NoticeResp>> listNotice(String openId, Integer startId, Integer type,
            Integer size, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<NoticeResp>>(-1, "未知的用户账号", null);
        }
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<ListWrapResp<NoticeResp>>(-1, "未找到此用户");
        }
        startId = ParameterUtils.formatStart(startId);
        size = ParameterUtils.formatStart(size);
        return noticeService.listNotice(userInfo.getId(), startId, type, size);
    }

    private UserInfo getUserInfo(String openId) throws ServiceException {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        return userInfoService.getInfoByUk(userInfo);
    }

}
