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
import com.dreambox.core.DbStatus;
import com.dreambox.core.cache.CacheFilter.IdStartSizeCacheFilter;
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
import com.dreambox.web.model.ListWrapResp;
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
    private UserAlbumInfoService userAlbumInfoService;
    @Autowired
    private UserAlbumItemInfoService userAlbumItemInfoService;
    @Autowired
    private UserAlbumCollectInfoService userAlbumCollectInfoService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private AlbumItemEditInfoService albumItemEditInfoService;
    @Autowired
    private UserAlbumItemEditInfoService userAlbumItemEditInfoService;

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
        } else if ((uploadStatus == AlbumUploadImgEnum.UPLOAD_NO_IMG.getStatus() && userAlbumItemInfo == null)
                || uploadStatus == AlbumUploadImgEnum.UPLOAD_NO_IMG_DELSTATUS.getStatus()) {
            // 将模版默认预览图赋值进去
            ua.setPreviewImgUrl(albumItemInfo.getPreviewImgUrl());
            userAlbumItemInfoService.addData(ua);
        }
        return handleIfAllItemUplaoded(userAlbumInfo, albumInfo);
    }

    private ApiRespWrapper<String> handleIfAllItemUplaoded(UserAlbumInfo userAlbumInfo, AlbumInfo albumInfo) {
        IdStartSizeCacheFilter idFilter = new IdStartSizeCacheFilter();
        idFilter.setId(userAlbumInfo.getAlbumId());
        ListWrapResp<AlbumItemInfo> itemInfos = albumItemInfoService.listInfo(idFilter);
        UserAlbumItemInfo g = new UserAlbumItemInfo();
        g.setStatus(DbStatus.STATUS_OK);
        g.setUserAlbumId(userAlbumInfo.getId());
        List<UserAlbumItemInfo> userItemInfos = userAlbumItemInfoService.listDirectFromDb(g);
        if (canGenPoster(itemInfos, userItemInfos)) {
            JoinImgFileResp joinImgFileResp = joinUserAlbumImg(userAlbumInfo, userItemInfos);
            // 完成相册制作
            if (joinImgFileResp.isJoined()) {
                userAlbumInfo.setComplete(CompleteEnum.COMPLETED.getStatus());
                userAlbumInfo.setPreviewImg(joinImgFileResp.getUrlPath());
                String title = albumInfo.getTitle() + "-"
                        + DateUtils.formatStr(new Date(), DateUtils.YYYYMMDDHHMM_FORMAT);
                userAlbumInfo.setTitle(title);
                userAlbumInfoService.modifyUserAlbumInfoCompleteAndPreImg(userAlbumInfo);
                return new ApiRespWrapper<String>(0, "相册生成成功!", String.valueOf(userAlbumInfo.getId()));
            } else {
                return new ApiRespWrapper<String>(-1, "相册生成失败!");
            }
        }
        return new ApiRespWrapper<String>(0, "数据添加成功!", String.valueOf(userAlbumInfo.getId()));
    }

    /**
     * 生成海报图
     * 
     * @param userAlbumId
     * @param itemInfos
     * @param userItemInfos
     * @return
     */
    private boolean canGenPoster(ListWrapResp<AlbumItemInfo> itemInfos, List<UserAlbumItemInfo> userItemInfos) {
        for (AlbumItemInfo itemInfo : itemInfos.getResultList()) {
            boolean itemUploaded = false;
            for (UserAlbumItemInfo userAlbumItemInfo : userItemInfos) {
                if (userAlbumItemInfo.getAlbumItemId() == itemInfo.getId()) {
                    itemUploaded = true;
                    break;
                }
            }
            if (!itemUploaded) {
                return false;
            }
        }
        return true;
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
    public JoinImgFileResp joinUserAlbumImg(UserAlbumInfo info, List<UserAlbumItemInfo> uaItems) {
        // 获取用户相册所有的单页预览图
        List<String> prwImgList = new ArrayList<String>();
        for (UserAlbumItemInfo uaii : uaItems) {
            prwImgList.add(userAlbumItemInfoService.getPreviewImgPath(uaii));
        }
        JoinImgFileResp joinImgFileResp = imgService.joinPreviewImg(info.getUserId(), prwImgList);
        return joinImgFileResp;
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

        // 检查该用户相册单页数据是否已经上传完毕,若数据完整则制作相册子图
        if (checkComplete(userAlbumItemInfo)) {
            UserAlbumItemEditInfo a = new UserAlbumItemEditInfo();
            a.setUserAlbumItemId(userAlbumItemInfo.getId());
            // 获取对应子页的编辑信息数据
            List<UserAlbumItemEditInfo> editInfos = userAlbumItemEditInfoService.listDirectFromDb(a);
            // 获取对应子页的模版信息
            AlbumItemInfo i = albumItemInfoService.getDirectFromDb(userAlbumItemInfo.getAlbumItemId());
            List<MergeImgWithMultipartModel> datas = new ArrayList<MergeImgWithMultipartModel>();
            // 根据是否上传图片来填充合图所需的数据
            for (UserAlbumItemEditInfo userAlbumItemEditInfo : editInfos) {
                MergeImgWithMultipartModel data = new MergeImgWithMultipartModel();
                if (StringUtils.isNotBlank(userAlbumItemEditInfo.getUserOriginImgUrl())) {
                    // 上传图片，将上传的图片及图片编辑信息填充进去
                    data.setPath(userAlbumItemInfoService.getUserOriginImgPath(userAlbumItemEditInfo
                            .getUserOriginImgUrl()));
                    data.setUserAlbumItemEditInfo(userAlbumItemEditInfo);
                    data.setClipDefault(false);
                    datas.add(data);
                } else {
                    // 未上传图片，填充默认预览图对应的编辑信息以裁剪该编辑区域的默认图填充至合成图中
                    data.setPath(albumItemInfoService.getDefaultPreImgPath(i));
                    data.setUserAlbumItemEditInfo(userAlbumItemEditInfo);
                    data.setClipDefault(true);
                    datas.add(data);
                }
            }
            // 开始合成
            if (datas.size() > 0) {
                MergeImgFileResp mergeImgFileResp;
                try {
                    mergeImgFileResp = imgService.mergeToPreviewImgWithMultipart(
                            albumItemInfoService.getEditImgPath(i), i, datas);
                    // 合成完毕，设置子页预览图地址信息
                    userAlbumItemInfo.setPreviewImgUrl(mergeImgFileResp.getUrlPath());
                    userAlbumItemInfoService.addData(userAlbumItemInfo);
                } catch (Exception e) {
                    log.error("album lite make pre img fail!errMsg:" + e.getMessage());
                    return new ApiRespWrapper<String>(-1, "相册生成失异常!errMsg:" + e.getMessage());
                }
            }
        }

        // 根据子页rank与约定标记判断是否是最后一页的制作，现在是由多并发转线性操作，不存在乱序影响
        if (albumItemInfo.getRank() + 1 == albumInfo.getTotalItems()
                && CompleteEnum.COMPLETED.getStatus() == isComplete) {
            // 生成相册标题
            userAlbumInfo.setTitle(albumInfo.getTitle() + " "
                    + DateUtils.formatStr(new Date(), DateUtils.YYYYMMDDHHMM_FORMAT));
            userAlbumInfoService.modifyUserAlbumInfoTitle(userAlbumInfo);
            userAlbumInfo.setComplete(1);
            userAlbumInfoService.modifyUserAlbumInfoCompleteAndPreImg(userAlbumInfo);
            return new ApiRespWrapper<String>(0, "相册生成成功!", String.valueOf(userAlbumId));
        }
        return new ApiRespWrapper<String>(0, "数据添加成功!", String.valueOf(userAlbumId));
    }

    private boolean checkComplete(UserAlbumItemInfo item) {
        boolean isComplete = true;
        UserAlbumItemEditInfo g = new UserAlbumItemEditInfo();
        g.setUserAlbumItemId(item.getId());
        List<UserAlbumItemEditInfo> editInfos = userAlbumItemEditInfoService.listDirectFromDb(g);
        AlbumItemInfo info = albumItemInfoService.getDirectFromDb(item.getAlbumItemId());
        if (CollectionUtils.emptyOrNull(editInfos) || info == null || editInfos.size() != info.getEditCount()) {
            isComplete = false;
        }
        return isComplete;
    }
}
