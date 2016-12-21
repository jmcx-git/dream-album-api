package com.dream.album.service;

import org.springframework.web.multipart.MultipartFile;

import com.dream.album.model.AlbumEditImgInfoModel;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.web.model.ApiRespWrapper;

/**
 * @author liuxinglong
 * @date 2016年12月21日
 */
public interface AlbumUploadService {
    public ApiRespWrapper<String> albumUploadHandle(MultipartFile image, Integer userAlbumId, Integer albumItemId,
            AlbumEditImgInfoModel model, Integer uploadStatus);

    public boolean uploadAndMergeImg(UserAlbumItemInfo info, AlbumItemInfo item, MultipartFile image);

    public boolean joinUserAlbumImg(UserAlbumInfo info);
}
