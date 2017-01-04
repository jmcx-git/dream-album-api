package com.dream.album.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.dreambox.core.dto.MergeImgWithMultipartModel;
import com.dreambox.core.dto.album.AlbumInfo;
import com.dreambox.core.dto.album.AlbumItemEditInfo;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.AlbumUploadImgEnum;
import com.dreambox.core.dto.album.CompleteEnum;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.dto.album.UserAlbumItemEditInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.album.AlbumInfoService;
import com.dreambox.core.service.album.AlbumItemEditInfoService;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.core.service.album.UserAlbumCollectInfoService;
import com.dreambox.core.service.album.UserAlbumInfoService;
import com.dreambox.core.service.album.UserAlbumItemEditInfoService;
import com.dreambox.core.service.album.UserAlbumItemInfoService;
import com.dreambox.core.utils.DateUtils;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.utils.CollectionUtils;
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
    private AlbumItemEditInfoService albumItemEditInfoService;
    @Autowired
    private UserAlbumInfoService userAlbumInfoService;
    @Autowired
    private UserAlbumItemInfoService userAlbumItemInfoService;
    @Autowired
    private UserAlbumCollectInfoService userAlbumCollectInfoService;
    @Autowired
    private UserAlbumItemEditInfoService userAlbumItemEditInfoService;
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
        } else if ((uploadStatus == AlbumUploadImgEnum.UPLOAD_NO_IMG.getStatus() && userAlbumItemInfo == null)
                || uploadStatus == AlbumUploadImgEnum.UPLOAD_NO_IMG_DELSTATUS.getStatus()) {
            // 将模版默认预览图赋值进去
            ua.setPreviewImgUrl(albumItemInfo.getPreviewImgUrl());
            userAlbumItemInfoService.addData(ua);
            // 更新该用户相册操作到第几步
            userAlbumInfo.setStep(albumItemInfo.getRank());
            userAlbumInfoService.modifyUserAlbumInfoStep(userAlbumInfo);
        }

        if (albumItemInfo.getRank() + 1 == albumInfo.getTotalItems()) {
            UserAlbumItemInfo uaNew = new UserAlbumItemInfo();
            uaNew.setUserAlbumId(userAlbumInfo.getId());
            // 根据用户信息id拉取用户相册最新的操作记录历史
            List<UserAlbumItemInfo> uaItems = new ArrayList<UserAlbumItemInfo>();
            int times = 0;
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
                        times++;
                    } catch (InterruptedException e) {
                        log.warn("thread sleep catch a error : " + e.getMessage());
                        return new ApiRespWrapper<String>(-1, "相册生成失异常!errMsg:" + e.getMessage());
                    }
                }
            } while (times < 10);
            if (times >= 10) {
                return new ApiRespWrapper<String>(-1, "相册生成失败,时间超时!");
            }
            boolean success = joinUserAlbumImg(userAlbumInfo, uaItems);
            if (success) {
                // 生成相册标题
                userAlbumInfo.setTitle(albumInfo.getTitle() + " "
                        + DateUtils.formatStr(new Date(), DateUtils.YYYYMMDDHHMM_FORMAT));
                userAlbumInfoService.modifyUserAlbumInfoTitle(userAlbumInfo);
                // 并发多时，可能step记录不准，若预览大图制作成功，直接修改step为最后一步(step暂无特殊用处)
                userAlbumInfo.setStep(albumItemInfo.getRank());
                userAlbumInfoService.modifyUserAlbumInfoStep(userAlbumInfo);
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

    /**
     * Lite版图片处理逻辑
     * 
     */
    @Override
    public ApiRespWrapper<String> albumUploadHandleLite(MultipartFile image, Integer userAlbumId, Integer albumItemId,
            AlbumEditImgInfoModel model, Integer uploadStatus, Integer index, Integer isComplete) {
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
        // ua.setPreviewImgUrl(albumItemInfo.getPreviewImgUrl());
        userAlbumItemInfoService.addData(ua);
        UserAlbumItemInfo userAlbumItemInfo = userAlbumItemInfoService.getUserAlbumItemInfoByUk(ua);
        if (userAlbumItemInfo == null) {
            return new ApiRespWrapper<String>(-1, "发生未知错误,用户相册子项记录入库失败,未取到相关记录信息!");
        }
        // 插入该用户相册子项对应的图片以及操作记录的数据
        UserAlbumItemEditInfo g = new UserAlbumItemEditInfo();
        g.setUserAlbumItemId(userAlbumItemInfo.getId());
        g.setRank(index);
        if (uploadStatus == AlbumUploadImgEnum.UPLOAD_IMG.getStatus() && image != null) {
            UploadFileSaveResp uploadFileSaveResp = imgService.handleUserUploadImg(image);
            g.setUserOriginImgUrl(uploadFileSaveResp.getUrlPath());
            g.setCssElmMoveX(model.getCssElmMoveX());
            g.setCssElmMoveY(model.getCssElmMoveY());
            g.setCssElmWidth(model.getCssElmWidth());
            g.setCssElmHeight(model.getCssElmHeight());
            g.setCssElmRotate(model.getCssElmRotate());
            userAlbumItemEditInfoService.addData(g);
        } else if (uploadStatus == AlbumUploadImgEnum.UPLOAD_NO_IMG.getStatus()) {
            // 模版默认定位
            AlbumItemEditInfo x = new AlbumItemEditInfo();
            x.setAlbumItemId(albumItemId);
            x.setRank(index);
            AlbumItemEditInfo template = albumItemEditInfoService.getAlbumItemEditInfoByUk(x);
            g.setCssElmMoveX(template.getCssElmMoveX());
            g.setCssElmMoveY(template.getCssElmMoveY());
            g.setCssElmWidth(template.getCssElmWidth());
            g.setCssElmHeight(template.getCssElmHeight());
            g.setCssElmRotate(template.getCssElmRotate());
            userAlbumItemEditInfoService.addData(g);
        }

        if (albumItemInfo.getRank() + 1 == albumInfo.getTotalItems()
                && CompleteEnum.COMPLETED.getStatus() == isComplete) {
            UserAlbumItemInfo uaNew = new UserAlbumItemInfo();
            uaNew.setUserAlbumId(userAlbumInfo.getId());
            // 根据用户信息id拉取用户相册最新的操作记录历史
            List<UserAlbumItemInfo> uaItems = new ArrayList<UserAlbumItemInfo>();
            int times = 0;
            do {
                /**
                 * 控制一次上传多图并发时，只允许所有图片记录都生成以后才制作预览图
                 */
                uaItems = userAlbumItemInfoService.listDirectFromDb(uaNew);
                if (uaItems != null && uaItems.size() == albumInfo.getTotalItems() && checkComplete(uaItems)) {
                    break;
                } else {
                    try {
                        Thread.sleep(2000);
                        times++;
                    } catch (InterruptedException e) {
                        log.warn("thread sleep catch a error : " + e.getMessage());
                        return new ApiRespWrapper<String>(-1, "相册生成失异常!errMsg:" + e.getMessage());
                    }
                }
            } while (times < 10);
            if (times >= 10) {
                return new ApiRespWrapper<String>(-1, "相册生成失败,时间超时!");
            }
            // TODO
            // 数据完整以后开始合成单张预览图
            for (UserAlbumItemInfo info : uaItems) {
                // 循环用户相册子页数据
                UserAlbumItemEditInfo a = new UserAlbumItemEditInfo();
                a.setUserAlbumItemId(info.getId());
                // 获取对应子页的编辑信息数据
                List<UserAlbumItemEditInfo> editInfos = userAlbumItemEditInfoService.listDirectFromDb(a);
                // 获取对应子页的模版信息
                AlbumItemInfo i = albumItemInfoService.getDirectFromDb(info.getAlbumItemId());
                List<MergeImgWithMultipartModel> datas = new ArrayList<MergeImgWithMultipartModel>();
                for (UserAlbumItemEditInfo userAlbumItemEditInfo : editInfos) {
                    MergeImgWithMultipartModel data = new MergeImgWithMultipartModel();
                    if (StringUtils.isNotBlank(userAlbumItemEditInfo.getUserOriginImgUrl())) {
                        data.setPath(userAlbumItemInfoService.getUserOriginImgPath(userAlbumItemEditInfo
                                .getUserOriginImgUrl()));
                        data.setUserAlbumItemEditInfo(userAlbumItemEditInfo);
                        data.setClipDefault(false);
                        datas.add(data);
                    } else {
                        data.setPath(albumItemInfoService.getDefaultPreImgPath(i));
                        data.setUserAlbumItemEditInfo(userAlbumItemEditInfo);
                        data.setClipDefault(true);
                        datas.add(data);
                    }
                }
                if (datas.size() > 0) {
                    MergeImgFileResp mergeImgFileResp;
                    try {
                        mergeImgFileResp = imgService.mergeToPreviewImgWithMultipart(
                                albumItemInfoService.getEditImgPath(i), i, datas);
                        info.setPreviewImgUrl(mergeImgFileResp.getUrlPath());
                        userAlbumItemInfoService.addData(info);
                    } catch (Exception e) {
                        log.error("album lite make pre img fail!errMsg:" + e.getMessage());
                        return new ApiRespWrapper<String>(-1, "相册生成失异常!errMsg:" + e.getMessage());
                    }
                }
                // else {
                // info.setPreviewImgUrl(i.getPreviewImgUrl());
                // userAlbumItemInfoService.addData(info);
                // }
            }
            // 生成相册标题
            userAlbumInfo.setTitle(albumInfo.getTitle() + " "
                    + DateUtils.formatStr(new Date(), DateUtils.YYYYMMDDHHMM_FORMAT));
            userAlbumInfoService.modifyUserAlbumInfoTitle(userAlbumInfo);
            // 并发多时，可能step记录不准，直接修改step为最后一步(step暂无特殊用处)
            userAlbumInfo.setStep(3);
            userAlbumInfoService.modifyUserAlbumInfoStep(userAlbumInfo);
            userAlbumInfo.setComplete(1);
            userAlbumInfoService.modifyUserAlbumInfoCompleteAndPreImg(userAlbumInfo);
            return new ApiRespWrapper<String>(0, "相册生成成功!", String.valueOf(userAlbumId));
        }
        return new ApiRespWrapper<String>(0, "数据添加成功!", String.valueOf(userAlbumId));
    }

    private boolean checkComplete(List<UserAlbumItemInfo> items) {
        boolean isComplete = true;
        for (UserAlbumItemInfo item : items) {
            UserAlbumItemEditInfo g = new UserAlbumItemEditInfo();
            g.setUserAlbumItemId(item.getId());
            List<UserAlbumItemEditInfo> editInfos = userAlbumItemEditInfoService.listDirectFromDb(g);
            AlbumItemInfo info = albumItemInfoService.getDirectFromDb(item.getAlbumItemId());
            if (CollectionUtils.emptyOrNull(editInfos) || info == null || editInfos.size() != info.getEditCount()) {
                isComplete = false;
            }
        }
        return isComplete;
    }
}
