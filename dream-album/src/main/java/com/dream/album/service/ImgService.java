// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dream.album.model.AlbumEditImgInfoModel;
import com.dream.album.model.JoinImgFileResp;
import com.dream.album.model.MergeImgFileResp;
import com.dream.album.model.UploadFileSaveResp;
import com.dreambox.core.dto.MergeImgWithMultipartModel;
import com.dreambox.core.dto.album.AlbumCoverItemInfo;
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

    public MergeImgFileResp mergeToPreviewImg(String editImePath, String localPath, AlbumItemInfo albumItemInfo,
            AlbumEditImgInfoModel model) throws Exception;

    public MergeImgFileResp mergeToPreviewImgWithMultipart(String editImePath, AlbumItemInfo albumItemInfo,
            List<MergeImgWithMultipartModel> datas) throws Exception;

    public JoinImgFileResp joinPreviewImg(int userAlbumId, List<String> prwImgList);

    public String getAlbumItemEditImgPath(AlbumItemInfo info);

    public String getAlbumItemDefaultPreImgPath(AlbumItemInfo info);

    public String getAlbumItemDefaultPreImgPath(String url);

    public String getUserAlbumItemPreviewImgPath(UserAlbumItemInfo g);

    public String getUserAlbumItemUserOriginImgPath(UserAlbumItemInfo g);

    public String getAlbumCoverItemEditImgPath(AlbumCoverItemInfo info);

    public String getAlbumCoverItemDefaultPreImgPath(AlbumCoverItemInfo info);

    // public boolean isTemplatePreviewImg(UserAlbumItemInfo g);
}
