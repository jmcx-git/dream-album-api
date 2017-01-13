// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.model.NoticeResp;
import com.jmcxclub.dream.family.service.NoticeService;
import com.jmcxclub.dream.family.service.SystemNoticeInfoService;
import com.jmcxclub.dream.family.service.UserNoticeInfoService;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private UserNoticeInfoService userNoticeInfoService;
    @Autowired
    private SystemNoticeInfoService systemNoticeInfoService;

    @Override
    public boolean hasNotice(int userId) throws ServiceException {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<NoticeResp>> listNotice(int userId) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

}
