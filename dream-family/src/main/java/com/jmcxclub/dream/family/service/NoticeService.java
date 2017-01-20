// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import java.util.List;

import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.model.NoticeResp;
import com.jmcxclub.dream.family.model.WikiResp;

/**
 * 分成系统消息和用户消息两种
 * 
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public interface NoticeService {

    boolean hasNotice(int userId) throws ServiceException;

    ApiRespWrapper<ListWrapResp<NoticeResp>> listNotice(int userId, Integer startId, Integer type, int size)
            throws ServiceException;

    List<WikiResp> listWikis() throws ServiceException;

    WikiResp getWiki(Integer id) throws ServiceException;

}
