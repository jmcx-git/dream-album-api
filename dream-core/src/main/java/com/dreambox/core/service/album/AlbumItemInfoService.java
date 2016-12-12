// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.service.album;

import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.dreambox.web.exception.ServiceException;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
public abstract class AlbumItemInfoService extends AbstractSortedListCacheService<AlbumItemInfo> {

    public abstract AlbumItemInfo getAlbumItemInfoByUk(AlbumItemInfo info) throws ServiceException;

    public abstract String getEditImePath(AlbumItemInfo info);
}
