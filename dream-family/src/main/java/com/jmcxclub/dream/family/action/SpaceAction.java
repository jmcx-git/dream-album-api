// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.action;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dreambox.core.DbStatus;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.core.utils.DateUtils;
import com.dreambox.core.utils.ParameterUtils;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.dto.FeedTypeEnum;
import com.jmcxclub.dream.family.model.OccupantFootprintResp;
import com.jmcxclub.dream.family.model.SpaceFeedCommentListResp;
import com.jmcxclub.dream.family.model.SpaceFeedListResp;
import com.jmcxclub.dream.family.model.SpaceFeedResp;
import com.jmcxclub.dream.family.model.SpaceInfoResp;
import com.jmcxclub.dream.family.model.SpaceListResp;
import com.jmcxclub.dream.family.model.SpaceUserInteractionInfoResp;
import com.jmcxclub.dream.family.model.UploadFileSaveResp;
import com.jmcxclub.dream.family.model.UserFeedListResp;
import com.jmcxclub.dream.family.service.ImgService;
import com.jmcxclub.dream.family.service.SpaceService;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
@Controller
@RequestMapping("/space/*")
public class SpaceAction extends IosBaseAction {
    private static final Logger log = Logger.getLogger(SpaceAction.class);
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * after add redirect to home page. Invoker will redirect space/list.json
     * 
     * @param openId
     * @param name
     * @param version
     * @gender 0 其它 1:male 2 female
     * @born 出生日期可为空 yyyy-MM-dd yyyymmdd两种格式
     * @type 0:亲子空间 1恋爱空间
     * @info 可为空
     * @image 可为空
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @ResponseBody
    public ApiRespWrapper<Integer> addSpace(String openId, Integer gender, String name, String born, Integer type,
            @RequestParam(required = false) MultipartFile image, String info, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Integer>(-1, "未知的用户账号", null);
        }
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<Integer>(-1, "Illegal open id.");
        }
        String icon = null;
        if (image != null && !image.isEmpty()) {
            UploadFileSaveResp fileResp = imgService.saveSpaceIcon(image, openId);
            if (!fileResp.isSaved()) {
                log.error("Save user icon failed. Errmsg:" + fileResp.getErrmsg());
            } else {
                icon = fileResp.getUrlPath();
            }
        }
        Date bornDate = null;
        if (!StringUtils.isEmpty(born)) {
            try {
                bornDate = DateUtils.parseDateStr(born);
            } catch (Exception e) {
            }
        }
        type = type == null ? (image == null ? FeedTypeEnum.DIARY.getType() : FeedTypeEnum.PHOTO.getType()) : type;
        return spaceService.addSpace(userInfo.getId(), gender, name, bornDate, type, icon, icon, info);
    }

    @RequestMapping(value = "/add.json", method = RequestMethod.GET)
    @ResponseBody
    public ApiRespWrapper<Integer> addSpace(String openId, Integer gender, String name, String born, Integer type,
            String info, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Integer>(-1, "错误的用户账号.", null);
        }
        UserInfo userInfo = getUserInfo(openId);
        if (userInfo == null) {
            return new ApiRespWrapper<Integer>(-1, "未知的用户账号.");
        }
        String icon = null;
        Date bornDate = null;
        if (!StringUtils.isEmpty(born)) {
            try {
                bornDate = DateUtils.parseDateStr(born);
            } catch (Exception e) {
            }
        }
        type = type == null ? FeedTypeEnum.DIARY.getType() : type;
        return spaceService.addSpace(userInfo.getId(), gender, name, bornDate, type, icon, icon, info);
    }

    /**
     * 加入空间
     * 
     * @param openId
     * @param secert
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/join.json")
    @ResponseBody
    public ApiRespWrapper<Integer> joinSpace(String openId, String secert, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Integer>(-1, "未知的用户账号", null);
        }
        return spaceService.joinSpace(openId, secert);
    }

    /**
     * 重置此空间分享码,先前的将失效
     * 
     * @param openId
     * @param spaceId
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/secert/reset.json")
    @ResponseBody
    public ApiRespWrapper<String> secertSpace(String openId, Integer spaceId, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<String>(-1, "未知的用户账号", null);
        }
        if (spaceId == null || spaceId.intValue() <= 0) {
            return new ApiRespWrapper<String>(-1, "未知的空间", null);
        }
        return spaceService.secertSpace(openId, spaceId);
    }

    @RequestMapping("/icon/edit.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> editSpaceIcon(String openId, int spaceId,
            @RequestParam(required = false) MultipartFile image, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        String icon = null;
        if (image != null && !image.isEmpty()) {
            UploadFileSaveResp fileResp = imgService.saveSpaceIcon(image, openId);
            if (!fileResp.isSaved()) {
                log.error("Save user icon failed. Errmsg:" + fileResp.getErrmsg());
                return new ApiRespWrapper<Boolean>(false);
            } else {
                icon = fileResp.getUrlPath();
            }
        }
        if (!StringUtils.isEmpty(icon)) {
            return spaceService.editSpaceIcon(openId, spaceId, icon);
        } else {
            return new ApiRespWrapper<Boolean>(false);
        }
    }

    /**
     * 
     * @param openId
     * @param spaceId
     * @param name
     * @param version
     * @born 出生日期可为空 yyyy-MM-dd yyyymmdd两种格式
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/info/edit.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> editSpaceInfo(String openId, int spaceId, String name, String born, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        Date bornDate = null;
        if (!StringUtils.isEmpty(born)) {
            try {
                bornDate = DateUtils.parseDateStr(born);
            } catch (Exception e) {
            }
        }
        return spaceService.editSpace(openId, spaceId, name, bornDate);
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> deleteSpace(String openId, int spaceId, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        return spaceService.deleteSpace(openId, spaceId);
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<SpaceListResp>> listSpace(String openId, Integer start, Integer size,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<SpaceListResp>>(-1, "未知的用户账号", null);
        }
        start = ParameterUtils.formatStart(start);
        size = ParameterUtils.formatStart(size);
        return spaceService.listSpace(openId, start, size);
    }

    @RequestMapping("/detail.json")
    @ResponseBody
    public ApiRespWrapper<SpaceInfoResp> detailSpace(String openId, int spaceId, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<SpaceInfoResp>(-1, "未知的用户账号", null);
        }
        return spaceService.getSpaceInfo(openId, spaceId);
    }

    @RequestMapping("/feed/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> listSpaceFeed(String openId, int spaceId, Integer start,
            Integer size, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(-1, "未知的用户账号", null);
        }
        start = ParameterUtils.formatStart(start);
        size = ParameterUtils.formatStart(size);
        return spaceService.listSpaceFeed(openId, spaceId, start, size);
    }

    /**
     * 列出用户所发的所有信息
     * 
     * @param openId
     * @param start
     * @param size
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/user/feed/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<UserFeedListResp>> listUserFeed(String openId, Integer start, Integer size,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<UserFeedListResp>>(-1, "未知的用户账号", null);
        }
        start = ParameterUtils.formatStart(start);
        size = ParameterUtils.formatStart(size);
        return spaceService.listUserFeed(openId, start, size);
    }

    @RequestMapping("/occupant/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<OccupantFootprintResp>> listSpaceOccupant(String openId, int spaceId,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<OccupantFootprintResp>>(-1, "未知的用户账号", null);
        }
        return spaceService.listSpaceOccupantFootprint(openId, spaceId);
    }

    /**
     * 返回此用户在此空间的互动信息
     * 
     * @param openId
     * @param spaceId
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/user/interaction/info.json")
    @ResponseBody
    public ApiRespWrapper<SpaceUserInteractionInfoResp> getSpaceUserInteractionInfo(String openId, int spaceId,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<SpaceUserInteractionInfoResp>(-1, "未知的用户账号", null);
        }
        return spaceService.getSpaceUserInteractionInfo(openId, spaceId);
    }

    @RequestMapping("/feed/detail.json")
    @ResponseBody
    public ApiRespWrapper<SpaceFeedResp> getFeedDetail(String openId, int feedId, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<SpaceFeedResp>(-1, "未知的用户账号", null);
        }
        return spaceService.getFeedDetail(openId, feedId);
    }

    @RequestMapping("/feed/like.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> likeFeed(String openId, int feedId, Integer status, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        if (status == null || status != -1) {
            status = DbStatus.STATUS_OK;
        } else {
            status = DbStatus.STATUS_DEL;
        }
        return spaceService.likeFeed(openId, feedId, status);
    }

    @RequestMapping("/feed/comment/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<SpaceFeedCommentListResp>> listFeedComment(String openId, int feedId,
            Integer headCommentId, Integer start, Integer size, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedCommentListResp>>(-1, "未知的用户账号", null);
        }
        start = ParameterUtils.formatStart(start);
        size = ParameterUtils.formatStart(size);
        return spaceService.listFeedComment(openId, feedId, headCommentId, start, size);
    }

    /**
     * 
     * @param openId
     * @param spaceId
     * @param title
     * @param content
     * @param type DIARY(1, "日记"), AUDIO(2, "录音"), PHOTO(0, "照片"), VIDEO(3,
     *        "视频");
     * @param file 用户上传上来的图，现在只支持一张
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/feed/add.json")
    @ResponseBody
    public ApiRespWrapper<Integer> addFeed(String openId, int spaceId, String title, String content, int type,
            @RequestParam(required = false) MultipartFile file, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Integer>(-1, "未知的用户账号", null);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        userInfo = userInfoService.getInfoByUk(userInfo);
        if (userInfo == null) {
            return new ApiRespWrapper<Integer>(-1, "Illegal open id.");
        }
        // cover
        // illustration
        String cover = null;
        String illustration = null;
        if (file != null && !file.isEmpty()) {
            if (type == FeedTypeEnum.PHOTO.getType()) {
                UploadFileSaveResp uploadFileSaveResp = imgService.saveFeedImg(file, openId);
                if (uploadFileSaveResp.isSaved()) {
                    cover = uploadFileSaveResp.getUrlPath();
                    illustration = uploadFileSaveResp.getUrlPath();
                }
            }
        }
        return spaceService.addFeed(userInfo.getId(), spaceId, title, content, type, cover, illustration);
    }

    private UserInfo getUserInfo(String openId) throws ServiceException {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        return userInfoService.getInfoByUk(userInfo);
    }

    @RequestMapping("/feed/comment/add.json")
    @ResponseBody
    public ApiRespWrapper<Integer> addFeedComment(String openId, int feedId, Integer commentRefId, String comment,
            String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Integer>(-1, "未知的用户账号", null);
        }
        return spaceService.addFeedComment(openId, feedId, commentRefId, comment);
    }

    /**
     * 用户,空间所有者可删除评论
     * 
     * @param openId
     * @param feedId
     * @param comment
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/feed/comment/delete.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> deleteFeedComment(String openId, Integer feedId, Integer commentId, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        if (feedId == null || feedId.intValue() <= 0) {
            return new ApiRespWrapper<Boolean>(-1, "未知的记录", null);
        }
        if (commentId == null || commentId.intValue() <= 0) {
            return new ApiRespWrapper<Boolean>(-1, "未知的评论", null);
        }
        return spaceService.deleteFeedComment(openId, feedId, commentId);
    }
}
