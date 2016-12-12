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

import com.dream.album.model.JoinImgFileResp;
import com.dream.album.model.MergeImgFileResp;
import com.dream.album.model.UploadFileSaveResp;
import com.dream.album.service.ImgService;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.utils.EasyImage;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.IOUtils;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
@Service("imgService")
public class ImgServiceImpl implements ImgService {
    private static final Logger log = Logger.getLogger(ImgServiceImpl.class);
    @Value("${dream.album.useralbumitemuploadimglocalpath}")
    private String userAlbumItemUploadImgLocalPath;
    @Value("${dream.album.useralbumitemuploadimgprefixurl}")
    private String userAlbumItemUploadImgPrefixUrl;
    @Value("${dream.album.useralbumitempreviewimgurlprefix}")
    private String userAlbumItemPreviewImgUrlPrefix = "";
    @Value("${dream.album.useralbumitempreviewimglocaldir}")
    private String userAlbumItemPreviewImgLocalDir = "";

    @Value("${dream.album.useralbumpriviewimgprefixurl}")
    private String userAlbumPriviewImgPrefixUrl;
    @Value("${dream.album.useralbumpriviewimglocalpath}")
    private String userAlbumPriviewImgLocalPath;

    @Value("${dream.album.albumitemeditimgurlprefix}")
    private String albumItemEditImgUrlPrefix = "";
    @Value("${dream.album.albumitemeditimglocaldir}")
    private String albumItemEditImgLocalDir = "";


    @Override
    public UploadFileSaveResp handleUserUploadImg(MultipartFile image) throws ServiceException {
        // 保存用户自己上传的图片
        long cttime = new Date().getTime();
        String picName = "album_" + cttime + ".png";
        File outputfile = new File(userAlbumItemUploadImgLocalPath + picName);
        try {
            ImageIO.write(ImageIO.read(image.getInputStream()), "png", outputfile);
        } catch (IOException e) {
            // TODO
            log.info(e.getMessage());
        }
        String picUrl = userAlbumItemUploadImgPrefixUrl + picName;
        return new UploadFileSaveResp(outputfile.getPath(), picUrl);
    }

    @Override
    public MergeImgFileResp mergeToPreviewImg(String editImePath, String localPath, AlbumItemInfo item,
            String imgCssInfo) throws ServiceException {

        // // 在宽高转换之前保存用户上传图片的位置信息json
        // AlbumEditImgInfoModel model = new AlbumEditImgInfoModel(positionX,
        // positionY, rotate, width, height);
        // bgWidth = bgWidth == null ? 0 : bgWidth;// 750
        // bgHeight = bgHeight == null ? 0 : bgHeight;// 1330
        // // 宽高转换
        // float xPercent = 750f / bgWidth;
        // float yPercent = 1330f / bgHeight;
        // positionX = positionX == null ? 0 : Math.round((float) (positionX *
        // xPercent + 0.5));
        // positionY = positionY == null ? 0 : Math.round((float) (positionY *
        // yPercent + 0.5));
        // rotate = rotate == null ? 0 : rotate;
        // width = width == null ? 0 : Math.round((float) (width * xPercent +
        // 0.5));
        // height = height == null ? 0 : Math.round((float) (height * yPercent +
        // 0.5));
        // TODO
        // ImagePsUtils img = new ImagePsUtils();

        // try {
        // img.mergeBothImage(editImePath, localPath, positionX, positionY,
        // width, height, rotate,
        // ALBUM_PRE_IMAGE_LOCAL + picPreName);
        // } catch (IOException e) {
        // log.info(e.getMessage());
        // }
        // String picPreUrl = ALBUM_PRE_IMAGE_INTERNET + picPreName;
        return new MergeImgFileResp(localPath, localPath);
    }

    @Override
    public JoinImgFileResp joinPreviewImg(int userAlbumId, List<String> prwImgList, String type) {
        EasyImage e = new EasyImage();
        String subDir = String.valueOf(userAlbumId);
        String fileName = System.currentTimeMillis() + "." + type;
        String productPreImg = IOUtils.spliceFileName(IOUtils.spliceDirPath(userAlbumPriviewImgLocalPath, subDir),
                fileName);
        // 纵向拼接成品相册预览图
        e.joinImageListVertical(prwImgList.toArray(new String[prwImgList.size()]), "png", productPreImg);
        return new JoinImgFileResp(userAlbumPriviewImgLocalPath, userAlbumPriviewImgPrefixUrl);
    }

    @Override
    public String getAlbumItemEditImgPath(AlbumItemInfo info) {
        return StringUtils.replace(info.getEditImgUrl(), this.albumItemEditImgUrlPrefix, this.albumItemEditImgLocalDir);
    }

    @Override
    public String getUserAlbumItemPreviewImgPath(UserAlbumItemInfo g) {
        String previewImgUrl = g.getPreviewImgUrl();
        return previewImgUrl.replace(userAlbumItemPreviewImgUrlPrefix, userAlbumItemPreviewImgLocalDir);
    }
}
