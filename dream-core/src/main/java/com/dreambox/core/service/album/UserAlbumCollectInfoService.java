package com.dreambox.core.service.album;

import java.util.List;

import com.dreambox.core.dto.album.UserAlbumCollectInfo;
import com.dreambox.core.service.AbsCommonDataService;
import com.dreambox.web.exception.ServiceException;

public abstract class UserAlbumCollectInfoService extends AbsCommonDataService<UserAlbumCollectInfo> {

    public abstract List<Integer> getCollectAlbumInfoId(Integer userId) throws ServiceException;

}
