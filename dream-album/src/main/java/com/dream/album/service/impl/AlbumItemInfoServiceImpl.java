package com.dream.album.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.AlbumItemInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.service.album.AlbumItemInfoService;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Service("albumItemInfoService")
public abstract class AlbumItemInfoServiceImpl extends AlbumItemInfoService {
    @Autowired
    private AlbumItemInfoDao albumItemInfoDao;

    @Override
    public CommonDao<AlbumItemInfo> getCommonDao() {
        return albumItemInfoDao;
    }
}
