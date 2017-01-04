package com.dream.album.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dream.album.dto.AlbumHomePageModel;
import com.dream.album.dto.MyAlbumModel;
import com.dream.album.dto.PreviewWrapModel;
import com.dream.album.model.AlbumEditImgInfoModel;
import com.dream.album.model.UserMakeAlbumInfo;
import com.dream.album.service.AlbumUploadService;
import com.dream.album.service.ImgService;
import com.dreambox.core.dto.album.AlbumInfo;
import com.dreambox.core.dto.album.AlbumItemEditInfo;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.AlbumUploadImgEnum;
import com.dreambox.core.dto.album.CompleteEnum;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.album.AlbumInfoService;
import com.dreambox.core.service.album.AlbumItemEditInfoService;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.core.service.album.UserAlbumCollectInfoService;
import com.dreambox.core.service.album.UserAlbumInfoService;
import com.dreambox.core.service.album.UserAlbumItemInfoService;
import com.dreambox.core.utils.ParameterUtils;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.model.ApiRespWrapper;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/dream/album/common/*")
public class AlbumCommonAction extends IosBaseAction {
    // private static final Logger log =
    // Logger.getLogger(AlbumCommonAction.class);

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
    private ImgService imgService;
    @Autowired
    private AlbumUploadService albumUploadService;

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
        List<AlbumInfo> infos;
        if (StringUtils.isBlank(keyword)) {
            infos = albumInfoService.listDirectFromDb(null, start, size);
        } else {
            AlbumInfo info = new AlbumInfo();
            info.setKeyword("%" + keyword + "%");
            infos = albumInfoService.listDirectFromDb(info, start, size);
        }
        model.setAlbumList(infos);
        return model;
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
        UserAlbumInfo userAlbumInfo = initUserAlbumInfoInit(userId, albumId);
        AlbumItemInfo albumItemInfo = new AlbumItemInfo();
        albumItemInfo.setStatus(AlbumItemInfo.STATUS_OK);
        albumItemInfo.setAlbumId(albumId);
        List<AlbumItemInfo> albumItemInfos = albumItemInfoService.listDirectFromDb(albumItemInfo);
        userAlbumInfo = userAlbumInfoService.createAlbum(userAlbumInfo);
        List<UserAlbumItemInfo> userAlbumItemInfos = null;
        if (userAlbumInfo.getId() != 0) {
            UserAlbumItemInfo ua = new UserAlbumItemInfo();
            ua.setUserAlbumId(userAlbumInfo.getId());
            // 根据用户信息id拉取用户相册的操作记录历史
            userAlbumItemInfos = userAlbumItemInfoService.listDirectFromDb(ua);
        }
        return new UserMakeAlbumInfo(userAlbumInfo, albumItemInfos, userAlbumItemInfos);
    }

    /**
     * 相册Lite版选取模版列表接口
     * 
     * @param start
     * @param size
     * @return
     */
    @RequestMapping("/listalbums.json")
    @ResponseBody
    public List<AlbumInfo> selectAlbum(Integer start, Integer size) {
        start = ParameterUtils.formatStart(start);
        size = ParameterUtils.formatSize(size);
        List<AlbumInfo> infos = albumInfoService.listDirectFromDb(null, start, size);
        for (AlbumInfo albumInfo : infos) {
            AlbumItemInfo albumItemInfo = new AlbumItemInfo();
            albumItemInfo.setStatus(AlbumItemInfo.STATUS_OK);
            albumItemInfo.setAlbumId(albumInfo.getId());
            List<AlbumItemInfo> albumItemInfos = albumItemInfoService.listDirectFromDb(albumItemInfo);
            for (AlbumItemInfo info : albumItemInfos) {
                AlbumItemEditInfo g = new AlbumItemEditInfo();
                g.setAlbumItemId(info.getId());
                List<AlbumItemEditInfo> edits = albumItemEditInfoService.listDirectFromDb(g);
                info.setPhotoInfos(edits);
            }
            albumInfo.setAlbumItemList(albumItemInfos);
        }
        return infos;
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
    public ApiRespWrapper<String> uploadUserAlbumItem(MultipartFile image,
            @RequestParam(required = true) Integer userAlbumId, @RequestParam(required = true) Integer albumItemId,
            Integer cssElmMoveX, Integer cssElmMoveY, Integer cssElmRotate, Integer cssElmWidth, Integer cssElmHeight) {
        AlbumEditImgInfoModel model = new AlbumEditImgInfoModel(cssElmMoveX, cssElmMoveY, cssElmWidth, cssElmHeight,
                cssElmRotate);
        return albumUploadService.albumUploadHandle(image, userAlbumId, albumItemId, model,
                AlbumUploadImgEnum.UPLOAD_IMG.getStatus());
    }

    /**
     * 用户相册模版未上传图片，添加一条用户相册模版空记录
     * 
     * @param userAlbumId
     * @param albumItemId
     * @return
     */
    @RequestMapping("/uploademptypage.json")
    @ResponseBody
    public ApiRespWrapper<String> addUserAlbumItemPageEmptyInfo(Integer userAlbumId, Integer albumItemId,
            boolean isDeleteInfo) {
        return albumUploadService
                .albumUploadHandle(null, userAlbumId, albumItemId, null,
                        isDeleteInfo ? AlbumUploadImgEnum.UPLOAD_NO_IMG_DELSTATUS.getStatus()
                                : AlbumUploadImgEnum.UPLOAD_NO_IMG.getStatus());
    }

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
    public ApiRespWrapper<String> uploadAllImg(MultipartFile image, Integer userId, Integer albumId, Integer albumItemId) {
        if (userId == null || userId.intValue() <= 0 || albumId == null || albumId.intValue() <= 0
                || albumItemId == null || albumItemId.intValue() <= 0) {
            return new ApiRespWrapper<String>(-1, "参数不合法!");
        }
        UserAlbumInfo userAlbumInfo = initUserAlbumInfoInit(userId, albumId);
        userAlbumInfo = userAlbumInfoService.createAlbum(userAlbumInfo);
        return albumUploadService.albumUploadHandle(image, userAlbumInfo.getId(), albumItemId, null,
                AlbumUploadImgEnum.UPLOAD_IMG.getStatus());
    }

    private static UserAlbumInfo initUserAlbumInfoInit(int userId, int albumId) {
        UserAlbumInfo userAlbumInfo = new UserAlbumInfo();
        userAlbumInfo.setUserId(userId);
        userAlbumInfo.setAlbumId(albumId);
        userAlbumInfo.setComplete(CompleteEnum.INIT.getStatus());
        return userAlbumInfo;
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
    public ApiRespWrapper<String> uploadAllImg(Integer userId, Integer albumId, Integer albumItemId) {
        if (userId == null || userId.intValue() <= 0 || albumId == null || albumId.intValue() <= 0
                || albumItemId == null || albumItemId.intValue() <= 0) {
            return new ApiRespWrapper<String>(-1, "参数不合法!");
        }
        UserAlbumInfo userAlbumInfo = initUserAlbumInfoInit(userId, albumId);
        userAlbumInfo = userAlbumInfoService.createAlbum(userAlbumInfo); //
        return albumUploadService.albumUploadHandle(null, userAlbumInfo.getId(), albumItemId, null,
                AlbumUploadImgEnum.UPLOAD_NO_IMG.getStatus());
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
    public List<MyAlbumModel> getMyAlbumList(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        Integer uid = Integer.valueOf(userId);
        UserAlbumInfo info = new UserAlbumInfo();
        info.setUserId(uid);
        // 获取该userId已完成的相册
        info.setComplete(1);
        List<UserAlbumInfo> listDirectFromDb = userAlbumInfoService.listDirectFromDb(info);
        List<MyAlbumModel> resultData = new ArrayList<MyAlbumModel>();
        // 拼装数据
        UserAlbumItemInfo g = new UserAlbumItemInfo();
        for (UserAlbumInfo userAlbumInfo : listDirectFromDb) {
            MyAlbumModel model = new MyAlbumModel();
            AlbumInfo albumInfo = albumInfoService.getDirectFromDb(userAlbumInfo.getAlbumId());
            g.setUserAlbumId(userAlbumInfo.getId());
            g.setAlbumId(userAlbumInfo.getAlbumId());
            g.setRank(0);
            UserAlbumItemInfo item = userAlbumItemInfoService.getUserAlbumItemInfoByUk(g);
            model.setAlbumId(albumInfo.getId());
            model.setTitle(StringUtils.isBlank(userAlbumInfo.getTitle()) ? albumInfo.getTitle() : userAlbumInfo
                    .getTitle());
            model.setCover(item != null ? item.getPreviewImgUrl() : albumInfo.getCover());
            model.setPreviewImg(albumInfo.getPreviewImg());
            model.setUserAlbumId(userAlbumInfo.getId());
            model.setComplete(userAlbumInfo.getComplete());
            model.setProductImg(userAlbumInfo.getPreviewImg());
            resultData.add(model);
        }
        return resultData;
    }

    @RequestMapping("/getpreview.json")
    @ResponseBody
    public PreviewWrapModel getProductPre(Integer userAlbumId, Integer albumId) {
        if (albumId == null && userAlbumId == null) {
            return null;
        }
        PreviewWrapModel model = new PreviewWrapModel();
        if (userAlbumId != null) {
            UserAlbumInfo userAlbumInfo = userAlbumInfoService.getDirectFromDb(userAlbumId);
            if (userAlbumInfo == null) {
                return null;
            }
            UserAlbumItemInfo g = new UserAlbumItemInfo();
            g.setUserAlbumId(userAlbumId);
            List<UserAlbumItemInfo> listDirectFromDb = userAlbumItemInfoService.listDirectFromDb(g);
            List<String> list = new ArrayList<String>();
            for (UserAlbumItemInfo userAlbumItemInfo : listDirectFromDb) {
                list.add(userAlbumItemInfo.getPreviewImgUrl());
            }
            AlbumInfo album = albumInfoService.getDirectFromDb(userAlbumInfo.getAlbumId());
            if (list.size() == album.getTotalItems() && StringUtils.isNotBlank(userAlbumInfo.getPreviewImg())) {
                model.setMakeComplete(true);
            } else {
                model.setMakeComplete(false);
            }
            model.setLoopPreImgs(list);
            model.setBigPreImg(userAlbumInfo.getPreviewImg());
        } else {
            AlbumInfo album = albumInfoService.getDirectFromDb(albumId);
            if (album == null) {
                return null;
            }
            AlbumItemInfo g = new AlbumItemInfo();
            g.setAlbumId(albumId);
            g.setStatus(AlbumItemInfo.STATUS_OK);
            List<AlbumItemInfo> itemInfos = albumItemInfoService.listDirectFromDb(g);
            List<String> list = new ArrayList<String>();
            for (AlbumItemInfo itemInfo : itemInfos) {
                list.add(itemInfo.getPreviewImgUrl());
            }
            model.setLoopPreImgs(list);
            model.setBigPreImg(album.getPreviewImg());
            model.setMakeComplete(true);
        }
        return model;
    }

    @RequestMapping("/modifyuseralbuminfotitle.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyUserAlbumInfoTitle(Integer userAlbumId, String title) {
        if (userAlbumId == null || userAlbumId.intValue() <= 0) {
            return new ApiRespWrapper<Boolean>(-1, "参数不合法!", false);
        }
        UserAlbumInfo g = new UserAlbumInfo();
        g.setId(userAlbumId);
        g.setTitle(title);
        userAlbumInfoService.modifyUserAlbumInfoTitle(g);
        return new ApiRespWrapper<Boolean>(-1, "modify title success", true);
    }
}
