package com.dreambox.core.service.album;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.AbsCommonDataService;
import com.dreambox.web.exception.ServiceException;

public abstract class UserInfoService extends AbsCommonDataService<UserInfo> {

    public abstract int addDataAndReturnId(UserInfo g) throws ServiceException;

}