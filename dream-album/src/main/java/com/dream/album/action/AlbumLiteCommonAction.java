package com.dream.album.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dream.album.model.AlbumEditImgInfoModel;
import com.dream.album.service.AlbumUploadService;
import com.dream.album.service.ImgService;
import com.dreambox.core.dto.album.AlbumUploadImgEnum;
import com.dreambox.core.dto.album.CompleteEnum;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.service.album.AlbumInfoService;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.core.service.album.UserAlbumCollectInfoService;
import com.dreambox.core.service.album.UserAlbumInfoService;
import com.dreambox.core.service.album.UserAlbumItemInfoService;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.model.ApiRespWrapper;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/dream/album/lite/common/*")
public class AlbumLiteCommonAction extends IosBaseAction {

    @Autowired
    private AlbumInfoService albumInfoService;
    @Autowired
    private AlbumItemInfoService albumItemInfoService;
    @Autowired
    private UserAlbumInfoService userAlbumInfoService;
    @Autowired
    private UserAlbumItemInfoService userAlbumItemInfoService;
    @Autowired
    private UserAlbumCollectInfoService userAlbumCollectInfoService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private AlbumUploadService albumUploadService;


    /**
     * 用户上传图片生成预览图(有图片)
     * 
     * 可以将之前的两个接口替换
     * 
     * @param image
     * @param userId
     * @param albumId
     * @param albumItemId
     * @return
     */
    @RequestMapping("/uploaduserimg.json")
    @ResponseBody
    public ApiRespWrapper<String> uploadAllImg(MultipartFile image, Integer userId, Integer albumId,
            Integer albumItemId, Integer cssElmMoveX, Integer cssElmMoveY, Integer cssElmWidth, Integer cssElmHeight,
            Integer cssElmRotate, Integer index, Integer isComplete) {
        if (userId == null || userId.intValue() <= 0 || albumId == null || albumId.intValue() <= 0
                || albumItemId == null || albumItemId.intValue() <= 0 || index == null || index.intValue() < 0) {
            return new ApiRespWrapper<String>(-1, "参数不合法!");
        }
        AlbumEditImgInfoModel model = new AlbumEditImgInfoModel(cssElmMoveX, cssElmMoveY, cssElmWidth, cssElmHeight,
                cssElmRotate);
        UserAlbumInfo userAlbumInfo = initUserAlbumInfoInit(userId, albumId);
        userAlbumInfo = userAlbumInfoService.createAlbum(userAlbumInfo);
        return albumUploadService.albumUploadHandleLite(image, userAlbumInfo.getId(), albumItemId, model,
                AlbumUploadImgEnum.UPLOAD_IMG.getStatus(), index, isComplete);
    }

    /**
     * 用户上传图片生成预览图(无图片)
     * 
     * 可以将之前的两个接口替换
     * 
     * @param image
     * @param userId
     * @param albumId
     * @param albumItemId
     * @return
     */
    @RequestMapping("/uploadnotuserimg.json")
    @ResponseBody
    public ApiRespWrapper<String> uploadAllImg(Integer userId, Integer albumId, Integer albumItemId, Integer index,
            Integer isComplete) {
        if (userId == null || userId.intValue() <= 0 || albumId == null || albumId.intValue() <= 0
                || albumItemId == null || albumItemId.intValue() <= 0 || index == null || index.intValue() < 0) {
            return new ApiRespWrapper<String>(-1, "参数不合法!");
        }
        UserAlbumInfo userAlbumInfo = initUserAlbumInfoInit(userId, albumId);
        userAlbumInfo = userAlbumInfoService.createAlbum(userAlbumInfo); //
        return albumUploadService.albumUploadHandleLite(null, userAlbumInfo.getId(), albumItemId, null,
                AlbumUploadImgEnum.UPLOAD_NO_IMG.getStatus(), index, isComplete);
    }



    private static UserAlbumInfo initUserAlbumInfoInit(int userId, int albumId) {
        UserAlbumInfo userAlbumInfo = new UserAlbumInfo();
        userAlbumInfo.setUserId(userId);
        userAlbumInfo.setAlbumId(albumId);
        userAlbumInfo.setComplete(CompleteEnum.INIT.getStatus());
        return userAlbumInfo;
    }
}
