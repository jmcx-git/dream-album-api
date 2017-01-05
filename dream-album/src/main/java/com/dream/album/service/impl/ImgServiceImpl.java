// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dream.album.model.AlbumEditImgInfoModel;
import com.dream.album.model.JoinImgFileResp;
import com.dream.album.model.MergeImgFileResp;
import com.dream.album.model.UploadFileSaveResp;
import com.dream.album.service.ImgService;
import com.dreambox.core.dto.MergeImgWithMultipartModel;
import com.dreambox.core.dto.album.AlbumCoverItemInfo;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.utils.DateUtils;
import com.dreambox.core.utils.ImagePsUtils;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.IOUtils;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
@Service("imgService")
public class ImgServiceImpl implements ImgService {
    private static final Logger log = Logger.getLogger(ImgServiceImpl.class);
    @Value("${dream.album.imgprefixurl}")
    private String imgPrefixUrl;
    @Value("${dream.album.imglocaldir}")
    private String imgLocalDir;

    @Value("${dream.album.useralbumitemuploadimglocaldir}")
    private String userAlbumItemUploadImgLocalDir;

    @Value("${dream.album.useralbumitempreviewimglocaldir}")
    private String userAlbumItemPreviewImgLocalDir = "";
    @Value("${dream.album.useralbumpreviewimglocaldir}")
    private String userAlbumPriviewImgLocalDir;
    @Value("${dream.album.albumitemeditimglocaldir}")
    private String albumItemEditImgLocalDir = "";
    @Value("${dream.album.albumitempreviewimglocaldir}")
    private String albumItemPreviewImgLocalDir = "";

    @Override
    public UploadFileSaveResp handleUserUploadImg(MultipartFile image) throws ServiceException {
        // 保存用户自己上传的图片
        String suffix = IOUtils.getSuffix(image.getName());
        suffix = StringUtils.isEmpty(suffix) ? ".png" : suffix;
        String picName = "album_user_" + new Date().getTime() + suffix;
        String subDir = DateUtils.getTodayYmd();
        String dir = IOUtils.spliceDirPath(IOUtils.spliceDirPath(imgLocalDir, userAlbumItemUploadImgLocalDir), subDir);
        String filePath = IOUtils.spliceFileName(dir, picName);
        File outputfile = new File(filePath);
        outputfile.mkdirs();
        try {
            ImageIO.write(ImageIO.read(image.getInputStream()), suffix.substring(1), outputfile);
            outputfile.setReadable(true, false);
        } catch (IOException e) {
            log.info("Save user upload file failed. Image path:" + filePath + ", Errmsg:" + e.getMessage(), e);
            throw ServiceException.getInternalException("Save user upload file failed.");
        }
        return new UploadFileSaveResp(filePath, StringUtils.replace(filePath, imgLocalDir, imgPrefixUrl));
    }

    @Override
    public MergeImgFileResp mergeToPreviewImg(String editImePath, String localPath, AlbumItemInfo albumItemInfo,
            AlbumEditImgInfoModel model) throws Exception {
        String picName = "album_item_pre_" + new Date().getTime() + ".jpg";
        String subDir = DateUtils.getTodayYmd();
        String dir = IOUtils.spliceDirPath(IOUtils.spliceDirPath(imgLocalDir, userAlbumItemPreviewImgLocalDir), subDir);
        String filePath = IOUtils.spliceFileName(dir, picName);
        try {
            ImagePsUtils.gracefulMergeImagesToJpg(editImePath, albumItemInfo.getImgWidth(),
                    albumItemInfo.getImgHeight(), localPath, model.getCssElmMoveX(), model.getCssElmMoveY(),
                    model.getCssElmWidth(), model.getCssElmHeight(), filePath);
        } catch (IOException e) {
            log.error("Merge image file to preview file faied. Errmsg:" + e.getMessage(), e);
            throw ServiceException.getInternalException("Merge image files failed.");
        }
        return new MergeImgFileResp(filePath, StringUtils.replace(filePath, imgLocalDir, imgPrefixUrl));
    }

    @Override
    public JoinImgFileResp joinPreviewImg(int userAlbumId, List<String> prwImgList) {
        String subDir = String.valueOf(userAlbumId);
        String fileName = System.currentTimeMillis() + ".jpg";
        String dir = IOUtils.spliceDirPath(IOUtils.spliceDirPath(imgLocalDir, userAlbumPriviewImgLocalDir), subDir);
        String productPreImg = IOUtils.spliceFileName(dir, fileName);
        // 纵向拼接成品相册预览图
        ImagePsUtils.joinImageToJpg(prwImgList, productPreImg, true);
        return new JoinImgFileResp(productPreImg, productPreImg.replace(imgLocalDir, imgPrefixUrl), true);
    }

    @Override
    public String getAlbumItemEditImgPath(AlbumItemInfo info) {
        return StringUtils.replace(info.getEditImgUrl(), this.imgPrefixUrl, this.imgLocalDir);
    }

    @Override
    public String getAlbumItemDefaultPreImgPath(AlbumItemInfo info) {
        return StringUtils.replace(info.getPreviewImgUrl(), this.imgPrefixUrl, this.imgLocalDir);
    }

    @Override
    public String getAlbumItemDefaultPreImgPath(String url) {
        return StringUtils.replace(url, this.imgPrefixUrl, this.imgLocalDir);
    }

    @Override
    public String getUserAlbumItemPreviewImgPath(UserAlbumItemInfo g) {
        String previewImgUrl = g.getPreviewImgUrl();
        return previewImgUrl.replace(imgPrefixUrl, imgLocalDir);
    }

    @Override
    public String getUserAlbumItemUserOriginImgPath(UserAlbumItemInfo g) {
        String userOriginImgUrl = g.getUserOriginImgUrl();
        return userOriginImgUrl.replace(imgPrefixUrl, imgLocalDir);
    }

    @Override
    public MergeImgFileResp mergeToPreviewImgWithMultipart(String editImePath, AlbumItemInfo albumItemInfo,
            List<MergeImgWithMultipartModel> datas) throws Exception {
        String picName = "album_item_pre_" + new Date().getTime() + ".jpg";
        String subDir = DateUtils.getTodayYmd();
        String dir = IOUtils.spliceDirPath(IOUtils.spliceDirPath(imgLocalDir, userAlbumItemPreviewImgLocalDir), subDir);
        String filePath = IOUtils.spliceFileName(dir, picName);
        try {
            ImagePsUtils.gracefulMergeImagesToJpgWithMultipart(editImePath, albumItemInfo.getImgWidth(),
                    albumItemInfo.getImgHeight(), datas, filePath);
        } catch (IOException e) {
            log.error("Merge image file to preview file faied. Errmsg:" + e.getMessage(), e);
            throw ServiceException.getInternalException("Merge image files failed.");
        }
        return new MergeImgFileResp(filePath, StringUtils.replace(filePath, imgLocalDir, imgPrefixUrl));
    }

    @Override
    public String getAlbumCoverItemEditImgPath(AlbumCoverItemInfo info) {
        return StringUtils.replace(info.getEditImgUrl(), this.imgPrefixUrl, this.imgLocalDir);
    }

    @Override
    public String getAlbumCoverItemDefaultPreImgPath(AlbumCoverItemInfo info) {
        return StringUtils.replace(info.getPreviewImgUrl(), this.imgPrefixUrl, this.imgLocalDir);
    }


}
