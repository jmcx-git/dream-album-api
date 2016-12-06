package com.dream.info.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.info.dao.UserAlbumItemInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.album.UserAlbumItemInfoService;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Service("userAlbumItemInfoService")
public class UserAlbumItemInfoServiceImpl extends UserAlbumItemInfoService {
    @Autowired
    private UserAlbumItemInfoDao userAlbumItemInfoDao;

    @Override
    public CommonDao<UserAlbumItemInfo> getCommonDao() {
        return userAlbumItemInfoDao;
    }
}
