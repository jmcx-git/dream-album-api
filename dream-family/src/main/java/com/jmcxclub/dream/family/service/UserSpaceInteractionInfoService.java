// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.service.AbsCommonCacheDbUkPkLoadService;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public abstract class UserSpaceInteractionInfoService extends AbsCommonCacheDbUkPkLoadService<UserSpaceInteractionInfo> {

    public abstract void incrViews(int userId, int spaceId) throws ServiceException;

    public abstract void incrLikes(int userId, int spaceId) throws ServiceException;

    public abstract void decrLikes(int userId, int spaceId) throws ServiceException;

    public abstract void incrComments(int userId, int spaceId) throws ServiceException;

    public abstract void decrComments(int userId, int spaceId) throws ServiceException;

    public abstract void modifyStatusByUserIdAndSpaceId(int userId, int spaceId) throws ServiceException;
}
