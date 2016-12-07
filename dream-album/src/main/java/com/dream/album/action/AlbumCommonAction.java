package com.dream.album.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dream.album.dto.AlbumHomePageModel;
import com.dream.album.model.AlbumEditImgInfoModel;
import com.dreambox.core.dto.album.AlbumInfo;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.album.AlbumInfoService;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.core.service.album.UserAlbumInfoService;
import com.dreambox.core.service.album.UserAlbumItemInfoService;
import com.dreambox.core.utils.ParameterUtils;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.utils.CollectionUtils;
import com.dreambox.web.utils.GsonUtils;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/dream/album/common/*")
public class AlbumCommonAction extends IosBaseAction {
    private static final Logger log = Logger.getLogger(AlbumCommonAction.class);
    private static final String ALBUM_PRE_IMAGE_LOCAL = "/Users/liuxinglong/git/dream-album-api/dream-album/src/main/webapp/images/made/";
    @Autowired
    private AlbumInfoService albumInfoService;
    @Autowired
    private AlbumItemInfoService albumItemInfoService;
    @Autowired
    private UserAlbumInfoService userAlbumInfoService;
    @Autowired
    private UserAlbumItemInfoService userAlbumItemInfoService;

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
    public AlbumHomePageModel getHomepage(String keyword, Integer start, Integer size) {
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
            info.setKeyword(keyword);
            infos = albumInfoService.listDirectFromDb(info, start, size);
        }
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
    @RequestMapping("/startmakeuseralbum")
    @ResponseBody
    public List<UserAlbumItemInfo> createUserAlbum(String userId, Integer albumId) {
        UserAlbumInfo info = new UserAlbumInfo();
        info.setUserId(userId);
        info.setAlbumId(albumId);
        info.setComplete(0);
        // 查看数据库中该用户该相册未制作完成的数据(理论上该条件下是唯一记录)
        List<UserAlbumInfo> userAlbum = userAlbumInfoService.listDirectFromDb(info);
        if (CollectionUtils.emptyOrNull(userAlbum)) {
            // 新建记录
            userAlbumInfoService.addData(info);
            return null;
        } else {
            // 根据查到的记录获取用户相册信息id
            UserAlbumInfo userAlbumInfo = userAlbum.get(0);
            UserAlbumItemInfo ua = new UserAlbumItemInfo();
            ua.setUserAlbumId(userAlbumInfo.getId());
            // 根据用户信息id拉取用户相册的操作记录历史
            List<UserAlbumItemInfo> uaItems = userAlbumItemInfoService.listDirectFromDb(ua);
            return uaItems;
        }
    }

    /**
     * 上传相册item操作数据记录的接口
     * 
     * @param image
     * @param userId
     * @param albumId
     * @param step
     * @param positionX
     * @param positionY
     * @param rotate
     */
    @RequestMapping("/uploadalbumpage.json")
    @ResponseBody
    public void uploadUserAlbumItem(MultipartFile image, String userId, Integer albumId, Integer step,
            Integer positionX, Integer positionY, Integer rotate, Integer width, Integer height) {
        UserAlbumInfo info = new UserAlbumInfo();
        info.setUserId(userId);
        info.setAlbumId(albumId);
        info.setComplete(0);
        // 查看数据库中该用户该相册未制作完成的数据(理论上该条件下是唯一记录)
        List<UserAlbumInfo> userAlbum = userAlbumInfoService.listDirectFromDb(info);
        UserAlbumInfo userAlbumInfo = userAlbum.get(0);
        if (step > userAlbumInfo.getStep()) {
            userAlbumInfoService.modifyUserAlbumInfoStep(userAlbumInfo);
        }
        UserAlbumItemInfo ua = new UserAlbumItemInfo();
        ua.setUserAlbumId(userAlbumInfo.getId());
        ua.setAlbumId(userAlbumInfo.getAlbumId());
        // 在album中所有图片的第几张
        ua.setRank(step);
        // 保存图片
        String picName = "album_" + new Date().getTime() + ".png";
        try {
            File outputfile = new File(ALBUM_PRE_IMAGE_LOCAL + picName);
            ImageIO.write(ImageIO.read(image.getInputStream()), "png", outputfile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String picUrl = "http://10.1.1.197:8080/dream-album/images/made/" + picName;
        ua.setUserOriginImgUrl(picUrl);
        // 操作信息
        AlbumEditImgInfoModel model = new AlbumEditImgInfoModel(positionX, positionY, rotate, width, height);
        String cssJson = GsonUtils.toJson(model);
        ua.setEditImgInfos(cssJson);
        // TODO 生成priviewImg
        ua.setPreviewImgUrl("");
    }
}
