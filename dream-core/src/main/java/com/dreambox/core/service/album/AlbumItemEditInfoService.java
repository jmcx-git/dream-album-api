// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.service.album;

import com.dreambox.core.dto.album.AlbumItemEditInfo;
import com.dreambox.core.service.AbstractSortedListCacheService;
import com.dreambox.web.exception.ServiceException;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
public abstract class AlbumItemEditInfoService extends AbstractSortedListCacheService<AlbumItemEditInfo> {
    public abstract AlbumItemEditInfo getAlbumItemEditInfoByUk(AlbumItemEditInfo info) throws ServiceException;
}
