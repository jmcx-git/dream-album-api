package com.dream.album.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dream.album.model.AlbumEditImgInfoModel;
import com.dream.album.model.JoinImgFileResp;
import com.dream.album.model.MergeImgFileResp;
import com.dream.album.model.UploadFileSaveResp;
import com.dream.album.service.AlbumUploadService;
import com.dream.album.service.ImgService;
import com.dreambox.core.dto.album.AlbumInfo;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.AlbumUploadImgEnum;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.album.AlbumInfoService;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.core.service.album.UserAlbumCollectInfoService;
import com.dreambox.core.service.album.UserAlbumInfoService;
import com.dreambox.core.service.album.UserAlbumItemInfoService;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.utils.GsonUtils;

/**
 * @author liuxinglong
 * @date 2016年12月21日
 */
@Service("albumUploadService")
public class AlbumUploadServiceImpl implements AlbumUploadService {
    private static final Logger log = Logger.getLogger(AlbumUploadServiceImpl.class);
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

    /**
     * 相册页面上传数据处理
     */
    @Override
    public ApiRespWrapper<String> albumUploadHandle(MultipartFile image, Integer userAlbumId, Integer albumItemId,
            AlbumEditImgInfoModel model, Integer uploadStatus) {
        if (userAlbumId == null || userAlbumId.intValue() <= 0 || albumItemId == null || albumItemId.intValue() <= 0) {
            return new ApiRespWrapper<String>(-1, "userId不能为空!");
        }
        // 查看数据库中该用户该相册未制作完成的数据(理论上该条件下是唯一记录)
        UserAlbumInfo userAlbumInfo = userAlbumInfoService.getDirectFromDb(userAlbumId.intValue());// getData(userAlbumId.intValue());
        if (userAlbumInfo == null) {
            return new ApiRespWrapper<String>(-1, "未找到Id:" + userAlbumId + "的相关记录!");
        }
        AlbumInfo albumInfo = albumInfoService.getDirectFromDb(userAlbumInfo.getAlbumId());
        if (albumInfo == null) {
            return new ApiRespWrapper<String>(-1, "未找到相册模版ID为:" + userAlbumInfo.getAlbumId() + "项的模版相关记录!");
        }
        AlbumItemInfo albumItemInfo = albumItemInfoService.getDirectFromDb(albumItemId);
        if (albumItemInfo == null) {
            return new ApiRespWrapper<String>(-1, "未找到相册模版ID为:" + albumItemId + "项的模版相关记录!");
        }

        UserAlbumItemInfo ua = new UserAlbumItemInfo();
        ua.setUserAlbumId(userAlbumInfo.getId());
        ua.setAlbumId(userAlbumInfo.getAlbumId());
        ua.setRank(albumItemInfo.getRank());// 在album中所有图片的第几张
        ua.setAlbumItemId(albumItemId);
        ua.setEditImgInfos(model == null ? "{}" : GsonUtils.toJson(model));

        UserAlbumItemInfo userAlbumItemInfo = userAlbumItemInfoService.getUserAlbumItemInfoByUk(ua);

        if (uploadStatus == AlbumUploadImgEnum.UPLOAD_IMG.getStatus() && image != null) {
            boolean success = uploadAndMergeImg(ua, albumItemInfo, image);
            if (!success) {
                return new ApiRespWrapper<String>(-1, "合成预览图出错!");
            }
            userAlbumItemInfoService.addData(ua);// 保存操作数据至数据库
            // 更新该用户相册操作到第几步
            userAlbumInfo.setStep(albumItemInfo.getRank());
            userAlbumInfoService.modifyUserAlbumInfoStep(userAlbumInfo);
        } else if (uploadStatus == AlbumUploadImgEnum.UPLOAD_NO_IMG.getStatus() && userAlbumItemInfo == null) {
            // 将模版默认预览图赋值进去
            ua.setPreviewImgUrl(albumItemInfo.getPreviewImgUrl());
            userAlbumItemInfoService.addData(ua);
            // 更新该用户相册操作到第几步
            userAlbumInfo.setStep(albumItemInfo.getRank());
            userAlbumInfoService.modifyUserAlbumInfoStep(userAlbumInfo);
        } else {
            return new ApiRespWrapper<String>(-1, "图片上传异常,未找到合适的处理途径!");
        }

        if (albumItemInfo.getRank() + 1 == albumInfo.getTotalItems()) {
            UserAlbumItemInfo uaNew = new UserAlbumItemInfo();
            uaNew.setUserAlbumId(userAlbumInfo.getId());
            // 根据用户信息id拉取用户相册最新的操作记录历史
            List<UserAlbumItemInfo> uaItems = new ArrayList<UserAlbumItemInfo>();
            do {
                /**
                 * 控制一次上传多图并发时，只允许所有图片记录都生成以后才 制作海报预览图
                 */
                uaItems = userAlbumItemInfoService.listDirectFromDb(uaNew);
                if (uaItems != null && uaItems.size() == albumInfo.getTotalItems()) {
                    break;
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        log.warn("thread sleep catch a error : " + e.getMessage());
                    }
                }
            } while (true);
            boolean success = joinUserAlbumImg(userAlbumInfo, uaItems);
            if (success) {
                return new ApiRespWrapper<String>(0, "相册生成成功!", String.valueOf(userAlbumId));
            } else {
                return new ApiRespWrapper<String>(-1, "相册生成失败!");
            }
        }
        return new ApiRespWrapper<String>(0, "数据添加成功!", String.valueOf(userAlbumId));
    }

    /**
     * 上传并制作预览图
     */
    @Override
    public boolean uploadAndMergeImg(UserAlbumItemInfo info, AlbumItemInfo item, MultipartFile image) {
        UploadFileSaveResp uploadFileSaveResp = imgService.handleUserUploadImg(image);
        info.setUserOriginImgUrl(uploadFileSaveResp.getUrlPath());
        // 根据已上传数据生成该页的预览图
        MergeImgFileResp mergeImgFileResp;
        try {
            AlbumEditImgInfoModel albumItemModel = GsonUtils.fromJsonStr(item.getEditImgInfos(),
                    AlbumEditImgInfoModel.class);
            mergeImgFileResp = imgService.mergeToPreviewImg(albumItemInfoService.getEditImgPath(item),
                    uploadFileSaveResp.getLocalPath(), item, albumItemModel);
            info.setPreviewImgUrl(mergeImgFileResp.getUrlPath());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 制作预览大图
     */
    @Override
    public boolean joinUserAlbumImg(UserAlbumInfo info, List<UserAlbumItemInfo> uaItems) {
        // 获取用户相册所有的单页预览图
        List<String> prwImgList = new ArrayList<String>();
        for (UserAlbumItemInfo uaii : uaItems) {
            prwImgList.add(userAlbumItemInfoService.getPreviewImgPath(uaii));
        }
        JoinImgFileResp joinImgFileResp = imgService.joinPreviewImg(info.getUserId(), prwImgList);
        // 完成相册制作
        if (joinImgFileResp.isJoined()) {
            info.setComplete(1);
            info.setPreviewImg(joinImgFileResp.getUrlPath());
            userAlbumInfoService.modifyUserAlbumInfoCompleteAndPreImg(info);
            return true;
        }
        return false;
    }

}
