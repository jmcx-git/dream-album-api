// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dreambox.core.utils.DateUtils;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.IOUtils;
import com.jmcxclub.dream.family.model.UploadFileSaveResp;
import com.jmcxclub.dream.family.service.ImgService;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("imgService")
public class ImgServiceImpl implements ImgService {
    private static final Logger log = Logger.getLogger(ImgServiceImpl.class);
    @Value("${dream.family.imgprefixurl}")
    private String imgPrefixUrl;
    @Value("${dream.family.imglocaldir}")
    private String imgLocalDir;

    @Value("${dream.album.userspaceiconuploadimglocaldir}")
    private String userSpaceIconUploadImgLocalDir;
    @Value("${dream.album.userfeedimguploadimglocaldir}")
    private String userFeedImgUploadImgLocalDir;

    @Override
    public UploadFileSaveResp saveSpaceIcon(MultipartFile image, String openId) throws ServiceException {
        return saveImg(image, "space_" + openId + "_", userSpaceIconUploadImgLocalDir);
    }

    @Override
    public UploadFileSaveResp saveFeedImg(MultipartFile image, String openId) throws ServiceException {
        return saveImg(image, "feed_" + openId + "_", userFeedImgUploadImgLocalDir);
    }

    private UploadFileSaveResp saveImg(MultipartFile image, String filePrefix, String imageSubDir) {
        // 保存用户自己上传的图片
        log.info("Image name:" + image.getOriginalFilename());
        log.info("Image name:" + image.getName());
        String suffix = IOUtils.getSuffix(image.getOriginalFilename());
        suffix = StringUtils.isEmpty(suffix) ? ".jpg" : suffix;
        String picName = filePrefix + new Date().getTime() + suffix;
        String subDir = DateUtils.getTodayYmd();
        String dir = IOUtils.spliceDirPath(IOUtils.spliceDirPath(imgLocalDir, imageSubDir), subDir);
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
}
