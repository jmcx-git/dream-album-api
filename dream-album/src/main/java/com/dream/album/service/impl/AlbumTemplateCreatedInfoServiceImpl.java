package com.dream.album.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.AlbumTemplateCreatedInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.AlbumTemplateCreatedInfo;
import com.dreambox.core.service.album.AlbumTemplateCreatedInfoService;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Service("albumTemplateCreatedInfoService")
public abstract class AlbumTemplateCreatedInfoServiceImpl extends AlbumTemplateCreatedInfoService {
    @Autowired
    private AlbumTemplateCreatedInfoDao albumTemplateCreatedInfoDao;

    @Override
    public CommonDao<AlbumTemplateCreatedInfo> getCommonDao() {
        return albumTemplateCreatedInfoDao;
    }
}
