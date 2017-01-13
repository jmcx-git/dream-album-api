// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import com.dreambox.core.service.AbsCommonCacheDataService;
import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.dto.UserReadNoticeRecord;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
public abstract class UserReadNoticeRecordService extends AbsCommonCacheDataService<UserReadNoticeRecord> {

    public abstract void modifyReadTime(int userId) throws ServiceException;

}
