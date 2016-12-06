package com.dream.album.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.UserAlbumInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.service.album.UserAlbumInfoService;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Service("userAlbumInfoService")
public abstract class UserAlbumInfoServiceImpl extends UserAlbumInfoService {
    @Autowired
    private UserAlbumInfoDao userAlbumInfoDao;

    @Override
    public CommonDao<UserAlbumInfo> getCommonDao() {
        return userAlbumInfoDao;
    }
}
