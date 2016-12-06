package com.dream.album.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.AlbumTemplateHandleInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.AlbumTemplateHandleInfo;
import com.dreambox.core.service.album.AlbumTemplateHandleInfoService;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Service("albumTemplateHandleInfoService")
public abstract class AlbumTemplateHandleInfoServiceImpl extends AlbumTemplateHandleInfoService {
    @Autowired
    private AlbumTemplateHandleInfoDao albumTemplateHandleInfoDao;

    @Override
    public CommonDao<AlbumTemplateHandleInfo> getCommonDao() {
        return albumTemplateHandleInfoDao;
    }
}
