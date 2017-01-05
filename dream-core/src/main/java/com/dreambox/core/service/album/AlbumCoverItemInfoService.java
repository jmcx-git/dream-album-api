// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.service.album;

import com.dreambox.core.dto.album.AlbumCoverItemInfo;
import com.dreambox.core.service.AbsCommonCacheDbUkPkLoadService;

/**
 * @author mokous86@gmail.com create date: Jan 5, 2017
 *
 */
public abstract class AlbumCoverItemInfoService extends AbsCommonCacheDbUkPkLoadService<AlbumCoverItemInfo> {
    public abstract String getEditImgPath(AlbumCoverItemInfo info);

    public abstract String getDefaultPreImgPath(AlbumCoverItemInfo info);

}
