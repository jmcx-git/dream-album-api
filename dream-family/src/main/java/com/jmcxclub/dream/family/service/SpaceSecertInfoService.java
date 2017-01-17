// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.service.AbsCommonCacheDbUkPkLoadService;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dto.SpaceSecertInfo;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public abstract class SpaceSecertInfoService extends AbsCommonCacheDbUkPkLoadService<SpaceSecertInfo> {
    public abstract String resetSecert(int spaceId) throws ServiceException;

    public abstract String getSecert(int spaceId) throws ServiceException;
}
