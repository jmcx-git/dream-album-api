// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.service.album;

import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.dreambox.web.exception.ServiceException;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
public abstract class UserAlbumItemInfoService extends AbstractSortedListCacheService<UserAlbumItemInfo> {
    public abstract String getPreviewImgPath(UserAlbumItemInfo g);
    
    public abstract String getUserOriginImgPath(UserAlbumItemInfo g);

    public abstract UserAlbumItemInfo getUserAlbumItemInfoByUk(UserAlbumItemInfo info) throws ServiceException;
}
