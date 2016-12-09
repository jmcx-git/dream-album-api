package com.dream.album.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

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
import com.dreambox.core.dto.album.AlbumInfo;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.service.album.AlbumInfoService;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.core.service.album.UserAlbumCollectInfoService;
import com.dreambox.core.service.album.UserAlbumInfoService;
import com.dreambox.core.service.album.UserAlbumItemInfoService;
import com.dreambox.core.utils.EasyImage;
import com.dreambox.core.utils.ImagePsUtils;
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
    private static final String ALBUM_PRE_IMAGE_LOCAL = "/Users/liuxinglong/git/dream-album-api/dream-album/src/main/webapp/images/made/";
    private static final String ALBUM_PRE_IMAGE_INTERNET = "http://10.1.1.197:8080/dream-album/images/made/";
    private static final String ALBUM_IMAGE_LOCAL = "/Users/liuxinglong/git/dream-album-api/dream-album/src/main/webapp/images/";
    private static final String ALBUM_IMAGE_INTERNET = "http://10.1.1.197:8080/dream-album/images/";

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
        List<Integer> albumId = userAlbumCollectInfoService.getCollectAlbumInfoId(userId);
        if (albumId != null && albumId.size() > 0) {
            for (AlbumInfo g : infos) {
                if (albumId.contains(g.getId())) {
                    g.setCollect(1);
                } else {
                    g.setCollect(0);
                }
            }
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
    @RequestMapping("/startmakeuseralbum.json")
    @ResponseBody
    public List<UserAlbumItemInfo> createUserAlbum(String userId, Integer albumId) {
        UserAlbumInfo info = new UserAlbumInfo(userId, albumId, 0);
        // 查看数据库中该用户该相册未制作完成的数据(理论上该条件下是唯一记录)
        UserAlbumInfo userAlbum = userAlbumInfoService.getUserAlbumInfoByUk(info);
        if (userAlbum == null) {
            // 新建记录
            userAlbumInfoService.addData(info);
            return null;
        } else {
            // 根据查到的记录获取用户相册信息id
            UserAlbumItemInfo ua = new UserAlbumItemInfo();
            ua.setUserAlbumId(userAlbum.getId());
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
     * @param rank
     * @param positionX
     * @param positionY
     * @param rotate
     * @param width
     * @param height
     */
    @RequestMapping("/uploadalbumpage.json")
    @ResponseBody
    public ApiRespWrapper<String> uploadUserAlbumItem(MultipartFile image, String userId, Integer albumId,
            Integer rank, Integer positionX, Integer positionY, Integer rotate, Integer width, Integer height,
            Integer isMadeStatus) {
        positionX = positionX == null ? 0 : positionX;
        positionY = positionY == null ? 0 : positionY;
        rotate = rotate == null ? 0 : rotate;
        width = width == null ? 0 : width;
        height = height == null ? 0 : height;
        isMadeStatus = isMadeStatus == null ? 0 : isMadeStatus;
        if (StringUtils.isBlank(userId)) {
            return new ApiRespWrapper<String>(-1, "userId不能为空!");
        }

        UserAlbumInfo info = new UserAlbumInfo(userId, albumId, 0);
        // 查看数据库中该用户该相册未制作完成的数据(理论上该条件下是唯一记录)
        UserAlbumInfo userAlbumInfo = userAlbumInfoService.getUserAlbumInfoByUk(info);
        if (userAlbumInfo == null) {
            return new ApiRespWrapper<String>(-1, "未找到用户:" + userId + " 制作相册模版ID为:" + albumId + "的相关记录!");
        }

        UserAlbumItemInfo ua = new UserAlbumItemInfo();
        ua.setUserAlbumId(userAlbumInfo.getId());
        ua.setAlbumId(userAlbumInfo.getAlbumId());
        ua.setRank(rank);// 在album中所有图片的第几张

        // 保存用户自己上传的图片
        long cttime = new Date().getTime();
        String picName = "album_" + cttime + ".png";
        try {
            File outputfile = new File(ALBUM_PRE_IMAGE_LOCAL + picName);
            ImageIO.write(ImageIO.read(image.getInputStream()), "png", outputfile);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        String picUrl = ALBUM_PRE_IMAGE_INTERNET + picName;
        ua.setUserOriginImgUrl(picUrl);

        // 保存用户上传图片的位置信息json
        AlbumEditImgInfoModel model = new AlbumEditImgInfoModel(positionX, positionY, rotate, width, height);
        String cssJson = GsonUtils.toJson(model);
        ua.setEditImgInfos(cssJson);

        // 根据已上传数据生成该页的预览图
        ImagePsUtils img = new ImagePsUtils();
        String picPreName = "album_pre_" + cttime + ".png";
        AlbumItemInfo g = new AlbumItemInfo();
        g.setAlbumId(albumId);
        g.setRank(rank);
        AlbumItemInfo item = albumItemInfoService.getAlbumItemInfoByUk(g);// 根据相册模版ID和rank获得具体某一张模版的原始数据
        if (item == null) {
            return new ApiRespWrapper<String>(-1, "未找到相册模版ID为:" + albumId + "第" + rank + "项的模版相关记录!");
        }
        try {
            img.mergeBothImage(item.getEditImgUrl().replace(ALBUM_IMAGE_INTERNET, ALBUM_IMAGE_LOCAL),
                    ALBUM_PRE_IMAGE_LOCAL + picName, positionX, positionY, width, height, rotate, ALBUM_PRE_IMAGE_LOCAL
                            + picPreName);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        String picPreUrl = ALBUM_PRE_IMAGE_INTERNET + picPreName;
        ua.setPreviewImgUrl(picPreUrl);

        userAlbumItemInfoService.addData(ua);// 保存操作数据至数据库
        // 更新该用户相册操作到第几步
        if (rank > userAlbumInfo.getStep()) {
            userAlbumInfo.setStep(rank);
            userAlbumInfoService.modifyUserAlbumInfoStep(userAlbumInfo);
        }

        // 若isMadeStatus为1,表示执行最后一步并制作相册大图预览图
        if (isMadeStatus == 1) {
            UserAlbumItemInfo uaNew = new UserAlbumItemInfo();
            uaNew.setUserAlbumId(userAlbumInfo.getId());
            // 根据用户信息id拉取用户相册最新的操作记录历史
            List<UserAlbumItemInfo> uaItems = userAlbumItemInfoService.listDirectFromDb(uaNew);
            // 获取用户相册所有的单页预览图
            List<String> prwImgList = new ArrayList<String>();
            for (UserAlbumItemInfo userAlbumItemInfo : uaItems) {
                String previewImgUrl = userAlbumItemInfo.getPreviewImgUrl();
                prwImgList.add(previewImgUrl.replace(ALBUM_PRE_IMAGE_INTERNET, ALBUM_PRE_IMAGE_LOCAL));
            }
            EasyImage e = new EasyImage();
            String productPreImg = "/Users/liuxinglong/Desktop/test" + new Random().nextInt(10) + ".png";
            // 纵向拼接成品相册预览图
            boolean madeResult = e.joinImageListVertical(prwImgList.toArray(new String[prwImgList.size()]), "png",
                    productPreImg);
            // 完成相册制作
            if (madeResult) {
                userAlbumInfo.setComplete(1);
                userAlbumInfo.setPriviewImg(productPreImg);
                userAlbumInfoService.modifyUserAlbumInfoCompleteAndPreImg(userAlbumInfo);
            }
            return new ApiRespWrapper<String>(0, "相册生成成功!", productPreImg);
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
    public String madeUserAlbum(String userId, int albumId) {
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
            String previewImgUrl = userAlbumItemInfo.getPreviewImgUrl();
            prwImgList.add(previewImgUrl.replace(ALBUM_PRE_IMAGE_INTERNET, ALBUM_PRE_IMAGE_LOCAL));
        }
        EasyImage image = new EasyImage();
        String productPreImg = "/Users/liuxinglong/Desktop/test" + new Random().nextInt(10) + ".png";
        // 纵向拼接成品相册预览图
        boolean madeResult = image.joinImageListVertical(prwImgList.toArray(new String[prwImgList.size()]), "png",
                productPreImg);
        if (madeResult) {
            userAlbum.setComplete(1);
            userAlbum.setPriviewImg(productPreImg);
            userAlbumInfoService.modifyUserAlbumInfoCompleteAndPreImg(userAlbum);
        }
        return productPreImg;
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
