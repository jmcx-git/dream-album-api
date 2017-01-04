package com.dreambox.core.service.album;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.AbsCommonCacheDbUkPkLoadService;
import com.dreambox.web.exception.ServiceException;

public abstract class UserInfoService extends AbsCommonCacheDbUkPkLoadService<UserInfo> {
    public abstract int addDataAndReturnId(UserInfo g) throws ServiceException;

    public abstract void modifyUserInfo(UserInfo g) throws ServiceException;

}
