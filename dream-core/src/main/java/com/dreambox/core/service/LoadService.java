// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.util.List;

import com.dreambox.web.exception.ServiceException;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface LoadService {
    public static final int ROOTID_APPLESTOREAPPID = 1;
    public static final int ROOTID_APPLICATIONHISTORY = 2;
    public static final int ROOTID_LATESTAPPLICATIONID = 3;
    public static final int APPRANK = 4;
    public static final int BANNER = 5;
    public static final int PROMOTE = 6;

    public void cacheInitLoad() throws ServiceException;

    public void deltaCacheInitLoad(int startId) throws ServiceException;

    public void deltaCacheInitLoad(List<Integer> deltaIds) throws ServiceException;

}
