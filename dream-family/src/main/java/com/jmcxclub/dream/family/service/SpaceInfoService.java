// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import java.util.Date;

import com.dreambox.core.service.AbsCommonCacheDataService;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dto.SpaceInfo;

/**
 * 空间信息服务类
 * 
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public abstract class SpaceInfoService extends AbsCommonCacheDataService<SpaceInfo> {

    public abstract void modifyInfo(int id, String title, Date born) throws ServiceException;

    public abstract void modifyIcon(int spaceId, String icon) throws ServiceException;

}
