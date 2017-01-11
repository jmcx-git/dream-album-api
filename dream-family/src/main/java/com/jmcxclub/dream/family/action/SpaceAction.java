// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.action;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dreambox.core.DbStatus;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
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
import com.jmcxclub.dream.family.model.UploadFileSaveResp;
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
     * @param title
     * @param version
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/add.json")
    @ResponseBody
    public ApiRespWrapper<Integer> addSpace(String openId, String title, String darlingName, Date darlingBornDate,
            int darlingType, MultipartFile image, String info, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Integer>(-1, "未知的用户账号", null);
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
        return spaceService.addSpace(openId, title, darlingName, darlingBornDate, darlingType, icon, icon, info);
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
    public ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> joinSpace(String openId, String secert, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(-1, "未知的用户账号", null);
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
    public ApiRespWrapper<String> secertSpace(String openId, Integer spaceId, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<String>(-1, "未知的用户账号", null);
        }
        if (spaceId == null || spaceId.intValue() <= 0) {
            return new ApiRespWrapper<String>(-1, "未知的空间", null);
        }
        return spaceService.secertSpace(openId, spaceId);
    }

    @RequestMapping("/edit.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> editSpace(String openId, int spaceId, String title, String version)
            throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<Boolean>(-1, "未知的用户账号", null);
        }
        return spaceService.editSpace(openId, spaceId, title);
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

    @RequestMapping("/space/feed/list.json")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> listSpaceFeed(String openId, int spaceId, Integer start,
            Integer size, String version) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(-1, "未知的用户账号", null);
        }
        start = ParameterUtils.formatStart(start);
        return spaceService.listSpaceFeed(openId, spaceId, start, size);
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
        return spaceService.listFeedComment(openId, feedId, headCommentId, start, size);
    }

    @RequestMapping("/feed/add.json")
    @ResponseBody
    public ApiRespWrapper<Integer> addFeed(String openId, int spaceId, String title, String content, int type,
            MultipartFile file, String version) throws ServiceException {
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
