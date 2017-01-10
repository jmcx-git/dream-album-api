// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.service.AbsCommonCacheDataService;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dto.SpaceStatInfo;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public abstract class SpaceStatInfoService extends AbsCommonCacheDataService<SpaceStatInfo> {
    public abstract void incrViews(int id) throws ServiceException;

    public abstract void incrOccupants(int id) throws ServiceException;

    public abstract void decrOccupants(int id) throws ServiceException;

    public abstract void incrRecords(int id) throws ServiceException;

    public abstract void decrRecords(int id) throws ServiceException;

}
