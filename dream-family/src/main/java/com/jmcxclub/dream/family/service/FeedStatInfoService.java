// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.service.AbsCommonCacheDataService;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dto.FeedStatInfo;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public abstract class FeedStatInfoService extends AbsCommonCacheDataService<FeedStatInfo> {

    public abstract void incrLikes(int feedId) throws ServiceException;

    public abstract void decrLikes(int feedId) throws ServiceException;

    public abstract void incrComments(int feedId) throws ServiceException;

    public abstract void decrComments(int feedId) throws ServiceException;

}
