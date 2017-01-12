// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.model.MyInfoResp;
import com.jmcxclub.dream.family.model.NoticeResp;
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

    @RequestMapping(value = "/notice/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<NoticeResp>> listNotice(String openId, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<NoticeResp>>(-1, "未知的用户账号", null);
        }
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<ListWrapResp<NoticeResp>>(-1, "未找到此用户");
        }
        return noticeService.listNotice(userInfo.getId());
    }

    private UserInfo getUserInfo(String openId) throws ServiceException {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        return userInfoService.getInfoByUk(userInfo);
    }

}