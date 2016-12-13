// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dream.album.model.AlbumEditImgInfoModel;
import com.dream.album.model.JoinImgFileResp;
import com.dream.album.model.MergeImgFileResp;
import com.dream.album.model.UploadFileSaveResp;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.web.exception.ServiceException;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
public interface ImgService {
    /**
     * 保存用户上传图片
     * 
     * @param image
     * 
     * @return
     * @throws ServiceException
     */
    public UploadFileSaveResp handleUserUploadImg(MultipartFile image) throws ServiceException;

    // public MergeImgFileResp mergeToPreviewImg(String editImePath, String
    // localPath, AlbumItemInfo albumItemInfo,
    // AlbumEditImgInfoModel model) throws ServiceException;

    public JoinImgFileResp joinPreviewImg(int userAlbumId, List<String> prwImgList, String type);

    public String getAlbumItemEditImgPath(AlbumItemInfo info);

    public String getUserAlbumItemPreviewImgPath(UserAlbumItemInfo g);

    //
    // public MergeImgFileResp mergeToPreviewImg(int userId, String
    // originBgImgPath, String userUploadOrginImgPath,
    // AlbumEditImgInfoModel model, int uploadImgInDeviceWidth, int
    // uploadImgInDeviceHeight,
    // float userScaleImageX, float userScaleImageY, int imgInDeviceLeft, int
    // imgInDeviceTop) throws IOException;

    MergeImgFileResp mergeToPreviewImg(int userId, String originBgImgPath, String userUploadOrginImgPath,
            AlbumEditImgInfoModel model) throws IOException;

}
