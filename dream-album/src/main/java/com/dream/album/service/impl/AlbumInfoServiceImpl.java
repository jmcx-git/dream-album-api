package com.dream.album.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.AlbumInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.AlbumInfo;
import com.dreambox.core.service.album.AlbumInfoService;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Service("albumInfoService")
public abstract class AlbumInfoServiceImpl extends AlbumInfoService {
    @Autowired
    private AlbumInfoDao albumInfoDao;

    @Override
    public CommonDao<AlbumInfo> getCommonDao() {
        return albumInfoDao;
    }
}
