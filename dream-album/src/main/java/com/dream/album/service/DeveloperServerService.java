// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.service;

import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;

/**
 * @author mokous86@gmail.com create date: Dec 15, 2016
 *
 */
public interface DeveloperServerService {
    public ApiRespWrapper<Boolean> restart() throws ServiceException;

}
