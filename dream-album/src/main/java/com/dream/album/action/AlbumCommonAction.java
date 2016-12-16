package com.dream.album.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dream.album.dto.AlbumHomePageModel;
import com.dream.album.dto.MyAlbumModel;
import com.dream.album.model.AlbumEditImgInfoModel;
import com.dream.album.model.JoinImgFileResp;
import com.dream.album.model.MergeImgFileResp;
import com.dream.album.model.UploadFileSaveResp;
import com.dream.album.model.UserMakeAlbumInfo;
import com.dream.album.service.ImgService;
import com.dreambox.core.dto.album.AlbumInfo;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.album.AlbumInfoService;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.core.service.album.UserAlbumCollectInfoService;
import com.dreambox.core.service.album.UserAlbumInfoService;
import com.dreambox.core.service.album.UserAlbumItemInfoService;
import com.dreambox.core.utils.ParameterUtils;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.utils.GsonUtils;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/dream/album/common/*")
public class AlbumCommonAction extends IosBaseAction {
    private static final Logger log = Logger.getLogger(AlbumCommonAction.class);

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
     * 首页数据接口，获取关键字及相册列表
     * 
     * @param keyword
     * @param start
     * @param size
     * @return
     */
    @RequestMapping("/homepage.json")
    @ResponseBody
    public AlbumHomePageModel getHomepage(String keyword, Integer userId, Integer start, Integer size) {
        start = ParameterUtils.formatStart(start);
        size = ParameterUtils.formatSize(size);
        AlbumHomePageModel model = new AlbumHomePageModel();
        // 获取所有相册信息(目前缓存无效暂用直接用数据库取数据)
        // ListWrapResp<AlbumInfo> searchInfos =
        // albumInfoService.searchInfos(keyword, start, size);
        List<AlbumInfo> infos;
        if (StringUtils.isBlank(keyword)) {
            infos = albumInfoService.listDirectFromDb(null, start, size);
        } else {
            AlbumInfo info = new AlbumInfo();
            info.setKeyword("%" + keyword + "%");
            infos = albumInfoService.listDirectFromDb(info, start, size);
        }
        // List<Integer> albumId =
        // userAlbumCollectInfoService.getCollectAlbumInfoId(userId);
        // if (albumId != null && albumId.size() > 0) {
        // for (AlbumInfo g : infos) {
        // if (albumId.contains(g.getId())) {
        // g.setCollect(1);
        // } else {
        // g.setCollect(0);
        // }
        // }
        // }
        model.setAlbumList(infos);
        return model;
    }

    /**
     * 根据相册id返回相册模版子列表接口
     * 
     * @param id
     * @return
     */
    @RequestMapping("/getalbum.json")
    @ResponseBody
    public List<AlbumItemInfo> editAlbum(int id) {
        if (id == 0) {
            return null;
        }
        AlbumItemInfo info = new AlbumItemInfo();
        info.setStatus(AlbumItemInfo.STATUS_OK);
        info.setAlbumId(id);
        List<AlbumItemInfo> list = albumItemInfoService.listDirectFromDb(info);
        return list;
    }

    /**
     * 用户点击开始制作创建的用户相册信息数据
     * 
     * 若第一次制作则添加制作信息
     * 
     * 若存在制作中的记录则返回item操作历史
     * 
     * @param userId
     * @param albumId
     */
    @RequestMapping("/startmakeuseralbum.json")
    @ResponseBody
    public UserMakeAlbumInfo createUserAlbum(Integer userId, Integer albumId) {
        UserAlbumInfo info = new UserAlbumInfo(userId, albumId, 0);
        AlbumItemInfo albumItemInfo = new AlbumItemInfo();
        albumItemInfo.setStatus(AlbumItemInfo.STATUS_OK);
        albumItemInfo.setAlbumId(albumId);
        List<AlbumItemInfo> albumItemInfos = albumItemInfoService.listDirectFromDb(albumItemInfo);
        UserAlbumInfo userAlbumInfo = userAlbumInfoService.findLatestUncompleteUserAlbum(info);
        List<UserAlbumItemInfo> userAlbumItemInfos = null;
        if (userAlbumInfo == null) {
            // 新建记录
            userAlbumInfoService.addData(info);
            userAlbumInfo = userAlbumInfoService.findLatestUncompleteUserAlbum(info);
        } else {
            // 根据查到的记录获取用户相册信息id
            UserAlbumItemInfo ua = new UserAlbumItemInfo();
            ua.setUserAlbumId(userAlbumInfo.getId());
            // 根据用户信息id拉取用户相册的操作记录历史
            userAlbumItemInfos = userAlbumItemInfoService.listDirectFromDb(ua);
        }
        return new UserMakeAlbumInfo(userAlbumInfo, albumItemInfos, userAlbumItemInfos);
    }

    /**
     * 上传相册item操作数据记录的接口
     * 
     * @param image
     * @param userId
     * @param albumId
     * @param rank
     * @param positionX
     * @param positionY
     * @param rotate
     * @param width
     * @param height
     */
    @RequestMapping("/uploadalbumpage.json")
    @ResponseBody
    public ApiRespWrapper<String> uploadUserAlbumItem(MultipartFile image, Integer userAlbumId, Integer albumItemId,
            String cssElmMoveX, String cssElmMoveY, String cssUploadImgInDeviceWidth,
            String cssUploadImgInDeviceHeight,
            // Integer cssElmRotate, Integer cssElmWidth, Integer cssElmHeight,
            String cssElmUserScaleX, String cssElmUserScaleY) {
        if (userAlbumId == null || userAlbumId.intValue() <= 0) {
            return new ApiRespWrapper<String>(-1, "userId不能为空!");
        }
        // 查看数据库中该用户该相册未制作完成的数据(理论上该条件下是唯一记录)
        UserAlbumInfo userAlbumInfo = userAlbumInfoService.getData(userAlbumId.intValue());
        if (userAlbumInfo == null) {
            return new ApiRespWrapper<String>(-1, "未找到Id:" + userAlbumId + "的相关记录!");
        }
        AlbumInfo albumInfo = albumInfoService.getData(userAlbumInfo.getAlbumId());
        if (albumInfo == null) {
            return new ApiRespWrapper<String>(-1, "未找到相册模版ID为:" + userAlbumInfo.getAlbumId() + "项的模版相关记录!");
        }
        AlbumItemInfo albumItemInfo = albumItemInfoService.getData(albumItemId);
        if (albumItemInfo == null) {
            return new ApiRespWrapper<String>(-1, "未找到相册模版ID为:" + albumItemId + "项的模版相关记录!");
        }
        AlbumEditImgInfoModel model = new AlbumEditImgInfoModel(cssElmMoveX, cssElmMoveY, cssUploadImgInDeviceWidth,
                cssUploadImgInDeviceHeight, cssElmUserScaleX, cssElmUserScaleY);
        UserAlbumItemInfo ua = new UserAlbumItemInfo();
        ua.setUserAlbumId(userAlbumInfo.getId());
        ua.setAlbumId(userAlbumInfo.getAlbumId());
        ua.setRank(albumItemInfo.getRank());// 在album中所有图片的第几张

        String imgCssInfo = GsonUtils.toJson(model);
        ua.setEditImgInfos(imgCssInfo);

        UploadFileSaveResp uploadFileSaveResp = imgService.handleUserUploadImg(image);
        ua.setUserOriginImgUrl(uploadFileSaveResp.getUrlPath());

        MergeImgFileResp mergeImgFileResp;
        try {
            mergeImgFileResp = // 根据已上传数据生成该页的预览图
            imgService.mergeToPreviewImg(userAlbumInfo.getUserId(), albumItemInfoService.getEditImgPath(albumItemInfo),
                    uploadFileSaveResp.getLocalPath(), model);
            // , cssUploadImgInDeviceWidth, cssUploadImgInDeviceHeight,
            // cssElmUserScaleX, cssElmUserScaleY, cssElmMoveX, cssElmMoveY);
        } catch (IOException e) {
            log.error("Merge user upload file to preview img failed. Errmsg:" + e.getMessage(), e);
            return new ApiRespWrapper<String>(-1, "制图失败.");
        }
        ua.setPreviewImgUrl(mergeImgFileResp.getUrlPath());
        userAlbumItemInfoService.addData(ua);// 保存操作数据至数据库
        // 更新该用户相册操作到第几步
        userAlbumInfo.setStep(albumItemInfo.getRank());
        userAlbumInfoService.modifyUserAlbumInfoStep(userAlbumInfo);

        if (albumItemInfo.getRank() + 1 == albumInfo.getTotalItems()) {
            UserAlbumItemInfo uaNew = new UserAlbumItemInfo();
            uaNew.setUserAlbumId(userAlbumInfo.getId());
            // 根据用户信息id拉取用户相册最新的操作记录历史
            List<UserAlbumItemInfo> uaItems = userAlbumItemInfoService.listDirectFromDb(uaNew);
            // 获取用户相册所有的单页预览图
            List<String> prwImgList = new ArrayList<String>();
            for (UserAlbumItemInfo userAlbumItemInfo : uaItems) {
                prwImgList.add(userAlbumItemInfoService.getPreviewImgPath(userAlbumItemInfo));
            }
            JoinImgFileResp joinImgFileResp = imgService.joinPreviewImg(userAlbumInfo.getId(), prwImgList, "png");
            // 完成相册制作
            if (joinImgFileResp.isJoined()) {
                userAlbumInfo.setComplete(1);
                userAlbumInfo.setPriviewImg(joinImgFileResp.getUrlPath());
                userAlbumInfoService.modifyUserAlbumInfoCompleteAndPreImg(userAlbumInfo);
                return new ApiRespWrapper<String>(0, "相册生成成功!", joinImgFileResp.getUrlPath());
            }
        }
        return new ApiRespWrapper<String>(0, "数据上传成功!");
    }

    /**
     * 
     * 点击完成制作，生成最终成品预览图
     * 
     * @param userId
     * @param albumId
     * @return
     */
    @RequestMapping("/madeuseralbum.json")
    @ResponseBody
    public String madeUserAlbum(Integer userId, int albumId) {
        UserAlbumInfo info = new UserAlbumInfo(userId, albumId, 0);
        // 查看数据库中该用户该相册未制作完成的数据(理论上该条件下是唯一记录)
        UserAlbumInfo userAlbum = userAlbumInfoService.getUserAlbumInfoByUk(info);

        // 根据查到的记录获取用户相册信息id
        UserAlbumItemInfo ua = new UserAlbumItemInfo();
        ua.setUserAlbumId(userAlbum.getId());
        // 根据用户信息id拉取用户相册的操作记录历史
        List<UserAlbumItemInfo> uaItems = userAlbumItemInfoService.listDirectFromDb(ua);
        List<String> prwImgList = new ArrayList<String>();
        for (UserAlbumItemInfo userAlbumItemInfo : uaItems) {
            prwImgList.add(userAlbumItemInfoService.getPreviewImgPath(userAlbumItemInfo));
        }
        JoinImgFileResp joinImgFileResp = imgService.joinPreviewImg(userAlbum.getId(), prwImgList, "png");
        if (joinImgFileResp.isJoined()) {
            userAlbum.setComplete(1);
            userAlbum.setPriviewImg(joinImgFileResp.getUrlPath());
            userAlbumInfoService.modifyUserAlbumInfoCompleteAndPreImg(userAlbum);
        }
        return joinImgFileResp.getUrlPath();
    }

    /**
     * 
     * 获取该userId已创建完成的相册
     * 
     * @param userId
     * @return
     */
    @RequestMapping("/myalbum.json")
    @ResponseBody
    public List<MyAlbumModel> getMyAlbumList(Integer userId) {
        if (userId == null || userId <= 0) {
            return null;
        }
        UserAlbumInfo info = new UserAlbumInfo();
        info.setUserId(userId);
        // 获取该userId已完成和制作中的的相册
        info.setComplete(UserAlbumInfo.STATUS_ALL);
        List<UserAlbumInfo> listDirectFromDb = userAlbumInfoService.listDirectFromDb(info);
        List<MyAlbumModel> resultData = new ArrayList<MyAlbumModel>();
        // 拼装数据
        for (UserAlbumInfo userAlbumInfo : listDirectFromDb) {
            MyAlbumModel model = new MyAlbumModel();
            AlbumInfo albumInfo = albumInfoService.getDirectFromDb(userAlbumInfo.getAlbumId());
            model.setAlbumId(albumInfo.getId());
            model.setTitle(albumInfo.getTitle());
            model.setCover(albumInfo.getCover());
            model.setPriviewImg(albumInfo.getPriviewImg());
            model.setUserAlbumId(userAlbumInfo.getId());
            model.setStep(userAlbumInfo.getStep());
            model.setComplete(userAlbumInfo.getComplete());
            model.setProductImg(userAlbumInfo.getPriviewImg());
            resultData.add(model);
        }
        return resultData;
    }
}
