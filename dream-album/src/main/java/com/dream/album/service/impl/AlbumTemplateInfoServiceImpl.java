package com.dream.album.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.AlbumTemplateInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.AlbumTemplateInfo;
import com.dreambox.core.service.album.AlbumTemplateInfoService;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Service("albumTemplateInfoService")
public abstract class AlbumTemplateInfoServiceImpl extends AlbumTemplateInfoService {
    @Autowired
    private AlbumTemplateInfoDao albumTemplateInfoDao;

    @Override
    public CommonDao<AlbumTemplateInfo> getCommonDao() {
        return albumTemplateInfoDao;
    }
}
