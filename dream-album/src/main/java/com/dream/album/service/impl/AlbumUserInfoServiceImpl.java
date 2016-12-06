package com.dream.album.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.AlbumUserInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.AlbumUserInfo;
import com.dreambox.core.service.album.AlbumUserInfoService;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Service("albumUserInfoService")
public abstract class AlbumUserInfoServiceImpl extends AlbumUserInfoService {
    @Autowired
    private AlbumUserInfoDao albumUserInfoDao;

    @Override
    public CommonDao<AlbumUserInfo> getCommonDao() {
        return albumUserInfoDao;
    }
}
