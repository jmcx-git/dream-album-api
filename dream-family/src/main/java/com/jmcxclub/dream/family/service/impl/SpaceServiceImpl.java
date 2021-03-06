// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreambox.core.StatusType;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.dreambox.web.utils.CollectionUtils;
import com.dreambox.web.utils.GsonUtils;
import com.jmcxclub.dream.family.dto.FeedCommentInfo;
import com.jmcxclub.dream.family.dto.FeedInfo;
import com.jmcxclub.dream.family.dto.FeedInnerPhoto;
import com.jmcxclub.dream.family.dto.FeedLikeInfo;
import com.jmcxclub.dream.family.dto.SpaceInfo;
import com.jmcxclub.dream.family.dto.SpaceRelationshipEnum;
import com.jmcxclub.dream.family.dto.SpaceSecertInfo;
import com.jmcxclub.dream.family.dto.SpaceStatInfo;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;
import com.jmcxclub.dream.family.dto.UserSpaceRelationshipInfo;
import com.jmcxclub.dream.family.model.FeedCommentInfoResp;
import com.jmcxclub.dream.family.model.OccupantFootprintResp;
import com.jmcxclub.dream.family.model.SpaceDetailResp;
import com.jmcxclub.dream.family.model.SpaceFeedCommentListResp;
import com.jmcxclub.dream.family.model.SpaceFeedListResp;
import com.jmcxclub.dream.family.model.SpaceFeedResp;
import com.jmcxclub.dream.family.model.SpaceInfoResp;
import com.jmcxclub.dream.family.model.SpaceListResp;
import com.jmcxclub.dream.family.model.SpaceUserInteractionInfoResp;
import com.jmcxclub.dream.family.model.UserFeedListResp;
import com.jmcxclub.dream.family.model.UserInfoResp;
import com.jmcxclub.dream.family.service.FeedCommentInfoService;
import com.jmcxclub.dream.family.service.FeedCommentInfoService.CommentSortedSetCacheFilter;
import com.jmcxclub.dream.family.service.FeedInfoService;
import com.jmcxclub.dream.family.service.FeedInfoService.FeedInfoSortedListCacheFilter;
import com.jmcxclub.dream.family.service.FeedLikeInfoService;
import com.jmcxclub.dream.family.service.FeedLikeInfoService.LikeInfoIdSortedSetCacheFilter;
import com.jmcxclub.dream.family.service.FeedStatInfoService;
import com.jmcxclub.dream.family.service.SpaceInfoService;
import com.jmcxclub.dream.family.service.SpaceSecertInfoService;
import com.jmcxclub.dream.family.service.SpaceService;
import com.jmcxclub.dream.family.service.SpaceStatInfoService;
import com.jmcxclub.dream.family.service.UserSpaceInteractionInfoService;
import com.jmcxclub.dream.family.service.UserSpaceRelationshipInfoService;
import com.jmcxclub.dream.family.service.UserSpaceRelationshipInfoService.UserSpaceRelationshipInfoSortedListCacheFilter;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
@Service("spaceService")
public class SpaceServiceImpl implements SpaceService {
    private static final Logger log = Logger.getLogger(SpaceServiceImpl.class);
    @Autowired
    private SpaceInfoService spaceInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SpaceStatInfoService spaceStatInfoService;
    @Autowired
    private FeedInfoService feedInfoService;
    @Autowired
    private SpaceSecertInfoService spaceSecertInfoService;
    @Autowired
    private FeedLikeInfoService feedLikeInfoService;
    @Autowired
    private FeedStatInfoService feedStatInfoService;
    @Autowired
    private FeedCommentInfoService feedCommentInfoService;
    @Autowired
    private UserSpaceRelationshipInfoService userSpaceRelationshipInfoService;
    @Autowired
    private UserSpaceInteractionInfoService userSpaceInteractionInfoService;

    @Override
    public ApiRespWrapper<ListWrapResp<SpaceListResp>> listSpace(String openId, int start, int size)
            throws ServiceException {
        UserInfo g = new UserInfo();
        g.setOpenId(openId);
        userInfoService.getInfoByUk(g);
        int userId = userInfoService.getIdByUk(g);
        if (userId <= 0) {
            return new ApiRespWrapper<ListWrapResp<SpaceListResp>>(-1, "未找到对应用户账号", null);
        }
        UserSpaceRelationshipInfoSortedListCacheFilter filter = new UserSpaceRelationshipInfoSortedListCacheFilter(
                null, userId, start, size);
        ListWrapResp<UserSpaceRelationshipInfo> spaceInfosResp = userSpaceRelationshipInfoService.listInfo(filter);
        List<Integer> spaceIds = new ArrayList<Integer>();
        for (UserSpaceRelationshipInfo spaceInfo : spaceInfosResp.getResultList()) {
            spaceIds.add(spaceInfo.getSpaceId());
        }
        List<SpaceInfo> infos = spaceInfoService.getData(spaceIds);
        List<SpaceStatInfo> stats = spaceStatInfoService.getData(spaceIds);
        List<SpaceListResp> datas = buildSpaceListResps(infos, stats, userId);
        ListWrapResp<SpaceListResp> data = new ListWrapResp<SpaceListResp>(spaceInfosResp.getTotalCount(), datas,
                spaceInfosResp.isMore(), spaceInfosResp.getNext());
        return new ApiRespWrapper<ListWrapResp<SpaceListResp>>(data);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<OccupantFootprintResp>> listSpaceOccupantFootprint(String openId, int spaceId)
            throws ServiceException {
        UserInfo g = new UserInfo();
        g.setOpenId(openId);
        UserInfo accessUserInfo = userInfoService.getInfoByUk(g);
        if (accessUserInfo == null) {
            return new ApiRespWrapper<ListWrapResp<OccupantFootprintResp>>(-2, "不存在用户OpenId", null);
        }
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null) {
            return new ApiRespWrapper<ListWrapResp<OccupantFootprintResp>>(-2, "不存在空间", null);
        }
        UserSpaceRelationshipInfoSortedListCacheFilter filter = new UserSpaceRelationshipInfoSortedListCacheFilter(
                spaceId, null, 0, Integer.MAX_VALUE);
        ListWrapResp<UserSpaceRelationshipInfo> infos = userSpaceRelationshipInfoService.listInfo(filter);
        List<OccupantFootprintResp> resultList = new ArrayList<OccupantFootprintResp>();
        List<Integer> userIds = new ArrayList<Integer>();
        boolean hasRelation = false;
        for (UserSpaceRelationshipInfo info : infos.getResultList()) {
            if (!hasRelation && info.getUserId() == accessUserInfo.getId()) {
                hasRelation = true;
            }
            userIds.add(info.getUserId());
        }
        if (!hasRelation) {
            return new ApiRespWrapper<ListWrapResp<OccupantFootprintResp>>(-2, "您暂无访问该空间的权限", null);
        }
        List<UserInfo> userInfos = userInfoService.getData(userIds);
        for (UserSpaceRelationshipInfo info : infos.getResultList()) {
            UserInfo spaceUser = null;
            UserSpaceInteractionInfo userSpaceInteractionInfo = null;
            for (UserInfo userInfo : userInfos) {
                if (userInfo.getId() == info.getUserId()) {
                    spaceUser = userInfo;
                }
            }
            if (spaceUser != null) {
                userSpaceInteractionInfo = new UserSpaceInteractionInfo();
                userSpaceInteractionInfo.setUserId(spaceUser.getId());
                userSpaceInteractionInfo.setSpaceId(spaceId);
                userSpaceInteractionInfo = userSpaceInteractionInfoService.getInfoByUk(userSpaceInteractionInfo);
            }

            resultList.add(new OccupantFootprintResp(userSpaceInteractionInfo, spaceUser, accessUserInfo.getId()));
        }
        ListWrapResp<OccupantFootprintResp> datas = new ListWrapResp<OccupantFootprintResp>(resultList);
        return new ApiRespWrapper<ListWrapResp<OccupantFootprintResp>>(datas);
    }

    @Override
    public ApiRespWrapper<SpaceDetailResp> getSpaceDetail(UserInfo userInfo, int spaceId) throws ServiceException {
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null) {
            return new ApiRespWrapper<SpaceDetailResp>(-1, "未知的空间.");
        }
        String secert = null;
        UserInfo owner = null;
        if (spaceInfo.getUserId() == userInfo.getId()) {
            secert = this.spaceSecertInfoService.getSecert(spaceId);
            owner = userInfo;
        } else {
            owner = userInfoService.getData(spaceInfo.getUserId());
        }
        SpaceStatInfo stat = spaceStatInfoService.getData(spaceId);
        UserSpaceRelationshipInfoSortedListCacheFilter filter = new UserSpaceRelationshipInfoSortedListCacheFilter(
                spaceId, null, 0, 5);
        ListWrapResp<UserSpaceRelationshipInfo> occupants = userSpaceRelationshipInfoService.listInfo(filter);
        List<Integer> userIds = new ArrayList<Integer>();
        for (UserSpaceRelationshipInfo userSpaceRelationshipInfo : occupants.getResultList()) {
            userIds.add(userSpaceRelationshipInfo.getUserId());
        }
        List<UserInfo> userInfos = userInfoService.getData(userIds);
        return new ApiRespWrapper<SpaceDetailResp>(new SpaceDetailResp(spaceInfo, owner, stat, userInfos, secert));
    }

    @Override
    public ApiRespWrapper<SpaceInfoResp> getSpaceInfo(UserInfo userInfo, int spaceId) throws ServiceException {
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null) {
            return new ApiRespWrapper<SpaceInfoResp>(-1, "未知的空间.");
        }
        UserInfo owner = null;
        if (spaceInfo.getUserId() == userInfo.getId()) {
            owner = userInfo;
        } else {
            owner = userInfoService.getData(spaceInfo.getUserId());
        }
        return new ApiRespWrapper<SpaceInfoResp>(new SpaceInfoResp(spaceInfo, owner));
    }

    private List<SpaceListResp> buildSpaceListResps(List<SpaceInfo> infos, List<SpaceStatInfo> stats, int accessUserId) {
        List<SpaceListResp> datas = new ArrayList<SpaceListResp>();
        for (SpaceInfo spaceInfo : infos) {
            SpaceStatInfo curStat = null;
            for (SpaceStatInfo stat : stats) {
                if (spaceInfo.getId() == stat.getId()) {
                    curStat = stat;
                }
            }
            boolean owner = spaceInfo.getUserId() == accessUserId;
            datas.add(new SpaceListResp(spaceInfo, curStat, owner));
        }
        return datas;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> listSpaceFeed(String openId, int spaceId, int start, int size)
            throws ServiceException {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        userInfo = userInfoService.getInfoByUk(userInfo);
        if (userInfo == null) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(-1, "未知的用户.", null);
        }
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(-1, "Illegal space id", null);
        }
        FeedInfoSortedListCacheFilter filter = new FeedInfoSortedListCacheFilter(null, spaceId, start, size);
        ListWrapResp<FeedInfo> infos = feedInfoService.listInfo(filter);
        afterViewSpace(userInfo.getId(), spaceId);
        if (infos == null || CollectionUtils.emptyOrNull(infos.getResultList())) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(new ListWrapResp<SpaceFeedListResp>(
                    new ArrayList<SpaceFeedListResp>(0)));
        }
        return buildFeedListResp(spaceInfo, infos, userInfo.getId());
    }

    private void afterViewSpace(int userId, int spaceId) {
        spaceStatInfoService.incrViews(spaceId);
        UserSpaceInteractionInfo g = new UserSpaceInteractionInfo();
        g.setUserId(userId);
        g.setSpaceId(spaceId);
        g.setViews(1);
        userSpaceInteractionInfoService.addData(g);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<UserFeedListResp>> listUserFeed(UserInfo userInfo, int start, int size)
            throws ServiceException {
        ListWrapResp<FeedInfo> infos = feedInfoService.listUserPhotoFeedInfo(userInfo.getId(), start, size);
        if (infos == null || CollectionUtils.emptyOrNull(infos.getResultList())) {
            return new ApiRespWrapper<ListWrapResp<UserFeedListResp>>(new ListWrapResp<UserFeedListResp>(
                    new ArrayList<UserFeedListResp>(0)));
        }
        return buildFeedListResp(infos, userInfo);
    }

    private ApiRespWrapper<ListWrapResp<UserFeedListResp>> buildFeedListResp(ListWrapResp<FeedInfo> infos,
            UserInfo userInfo) {
        List<UserFeedListResp> datas = new ArrayList<UserFeedListResp>();
        for (FeedInfo feedInfo : infos.getResultList()) {
            datas.add(new UserFeedListResp(feedInfo, userInfo));
        }
        return new ApiRespWrapper<ListWrapResp<UserFeedListResp>>(new ListWrapResp<>(infos.getTotalCount(), datas,
                infos.isMore(), infos.getNext()));
    }

    private ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> buildFeedListResp(SpaceInfo spaceInfo,
            ListWrapResp<FeedInfo> infos, int accessUserId) {
        List<Integer> userIds = new ArrayList<Integer>();
        Map<Integer, ListWrapResp<FeedLikeInfo>> feedIdLikeInfoMap = new HashMap<Integer, ListWrapResp<FeedLikeInfo>>(
                infos.getResultList().size());
        Map<Integer, ListWrapResp<FeedCommentInfo>> feedIdCommenteInfoMap = new HashMap<Integer, ListWrapResp<FeedCommentInfo>>(
                infos.getResultList().size());
        for (FeedInfo info : infos.getResultList()) {
            if (!userIds.contains(info.getUserId())) {
                userIds.add(info.getUserId());
            }
            LikeInfoIdSortedSetCacheFilter likeInfoIdSortedSetCacheFilter = new LikeInfoIdSortedSetCacheFilter(null,
                    info.getId(), 0, 5);
            ListWrapResp<FeedLikeInfo> likes = this.feedLikeInfoService.listInfo(likeInfoIdSortedSetCacheFilter);
            if (likes != null && CollectionUtils.notEmptyAndNull(likes.getResultList())) {
                feedIdLikeInfoMap.put(info.getId(), likes);
            }
            CommentSortedSetCacheFilter commentSortedSetCacheFilter = new CommentSortedSetCacheFilter(null,
                    info.getId(), 0, Integer.MAX_VALUE);
            ListWrapResp<FeedCommentInfo> comments = this.feedCommentInfoService.listInfo(commentSortedSetCacheFilter);
            if (comments != null && CollectionUtils.notEmptyAndNull(comments.getResultList())) {
                feedIdCommenteInfoMap.put(info.getId(), comments);
            }

        }
        for (Entry<Integer, ListWrapResp<FeedCommentInfo>> entry : feedIdCommenteInfoMap.entrySet()) {
            for (FeedCommentInfo comment : entry.getValue().getResultList()) {
                if (!userIds.contains(comment.getUserId())) {
                    userIds.add(comment.getUserId());
                }
            }
        }
        Map<Integer, List<Integer>> feedLikeUserIdMap = new HashMap<Integer, List<Integer>>();
        for (Entry<Integer, ListWrapResp<FeedLikeInfo>> entry : feedIdLikeInfoMap.entrySet()) {
            for (FeedLikeInfo likeInfo : entry.getValue().getResultList()) {
                if (!userIds.contains(likeInfo.getUserId())) {
                    userIds.add(likeInfo.getUserId());
                }
                try {
                    CollectionUtils.mapAddObject(entry.getKey(), feedLikeUserIdMap, likeInfo.getUserId());
                } catch (IllegalAccessException e) {
                }
            }

        }
        // GET user'info
        List<UserInfo> userInfos = this.userInfoService.getData(userIds);
        Map<Integer, UserInfo> userIdInfoMap = CollectionUtils.transformToMap(userInfos);
        return buildFeedListResp(infos, feedIdCommenteInfoMap, feedLikeUserIdMap, userIdInfoMap, accessUserId);
    }

    /**
     * like按时间先后排
     * 
     * 评论按时间后先排
     * 
     * @param infos
     * @param feedIdCommenteInfoMap
     * @param feedIdLikeInfoMap
     * @param userIdInfoMap
     * @return
     */
    private ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> buildFeedListResp(ListWrapResp<FeedInfo> infos,
            Map<Integer, ListWrapResp<FeedCommentInfo>> feedIdCommenteInfoMap,
            Map<Integer, List<Integer>> feedIdLikeInfoMap, Map<Integer, UserInfo> userIdInfoMap, int accessUserId) {
        if (infos == null || CollectionUtils.emptyOrNull(infos.getResultList())) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(new ListWrapResp<SpaceFeedListResp>(
                    new ArrayList<SpaceFeedListResp>(0)));
        }
        List<SpaceFeedListResp> datas = new ArrayList<SpaceFeedListResp>();
        for (FeedInfo feedInfo : infos.getResultList()) {
            UserInfo authorUserInfo = userIdInfoMap.get(feedInfo.getUserId());
            List<UserInfoResp> likeIcons = new ArrayList<UserInfoResp>();
            List<Integer> likeUserIds = feedIdLikeInfoMap.get(feedInfo.getId());
            List<FeedCommentInfoResp> comments = new ArrayList<FeedCommentInfoResp>();
            boolean ilike = false;
            if (likeUserIds != null) {
                for (Integer userId : likeUserIds) {
                    UserInfo userInfo = userIdInfoMap.get(userId);
                    if (userInfo != null) {
                        likeIcons.add(new UserInfoResp(userInfo));
                        ilike = ilike || userInfo.getId() == accessUserId;
                    }
                }
            }
            ListWrapResp<FeedCommentInfo> commentsResp = feedIdCommenteInfoMap.get(feedInfo.getId());
            if (commentsResp != null && CollectionUtils.notEmptyAndNull(commentsResp.getResultList())) {
                for (FeedCommentInfo feedCommentInfo : commentsResp.getResultList()) {
                    UserInfo commentUserInfo = userIdInfoMap.get(feedCommentInfo.getUserId());
                    comments.add(new FeedCommentInfoResp(feedCommentInfo, commentUserInfo));
                }
            }
            datas.add(new SpaceFeedListResp(feedInfo, authorUserInfo, likeIcons, comments, ilike));
        }
        return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(new ListWrapResp<SpaceFeedListResp>(
                infos.getTotalCount(), datas, infos.isMore(), infos.getNext()));
    }


    /**
     * 对多个文件上传的支持
     * 
     * @param id
     * @param spaceId
     * @param feedId
     * @param index
     * @param count
     * @param file
     * @param version
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean addMultiFeed(int userId, int spaceId, int feedId, String illustration, int index, int count, int type)
            throws ServiceException {
        String illustrationJson = GsonUtils.toJson(new FeedInnerPhoto(index, illustration));
        FeedInfo feedInfo = new FeedInfo();
        if (index == 0) {
            feedInfo.setCover(illustration);
        }
        feedInfo.setId(feedId);
        feedInfo.setSpaceId(spaceId);
        feedInfo.setUserId(userId);
        feedInfo.setIllustration(illustrationJson);
        return feedInfoService.modifyIllustration(feedInfo, count);
    }

    public ApiRespWrapper<Integer> addFeed(int userId, int spaceId, String title, String content, int type,
            String cover, String illustration) throws ServiceException {
        FeedInfo g = new FeedInfo();
        g.setContent(content);
        g.setTitle(title);
        g.setType(type);
        g.setCover(cover);
        g.setIllustration(illustration);
        g.setSpaceId(spaceId);
        g.setUserId(userId);
        g.setStatus(FeedInfo.STATUS_OK);
        feedInfoService.addData(g);
        try {
            spaceStatInfoService.incrRecords(spaceId);
        } catch (ServiceException e) {
            log.error("Incr space records failed. SpaceId:" + spaceId + ", Errmsg:" + e.getMessage());
        }
        return new ApiRespWrapper<Integer>(g.getId());
    }

    @Override
    public ApiRespWrapper<Integer> addMultiFeedFirst(int userId, int spaceId, String title, String content, int type)
            throws ServiceException {
        FeedInfo g = new FeedInfo();
        g.setContent(content);
        g.setTitle(title);
        g.setType(type);
        g.setSpaceId(spaceId);
        g.setUserId(userId);
        feedInfoService.addMultiFeedFirst(g);
        try {
            spaceStatInfoService.incrRecords(spaceId);
        } catch (ServiceException e) {
            log.error("Incr space records failed. SpaceId:" + spaceId + ", Errmsg:" + e.getMessage());
        }
        return new ApiRespWrapper<Integer>(g.getId());
    }

    @Override
    public ApiRespWrapper<Boolean> deleteFeed(int userId, int id) throws ServiceException {
        // permission
        FeedInfo feedInfo = feedInfoService.getData(id);
        if (feedInfo == null) {
            return new ApiRespWrapper<>(-1, "未知的记录", false);
        }
        if (feedInfo.getUserId() != userId) {
            return new ApiRespWrapper<>(-1, "你没有删除当前评论的权限.", false);
        }
        feedInfo.setStatus(FeedInfo.STATUS_DEL);
        feedInfoService.modifyStatus(feedInfo);
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<Integer> addFeedComment(String openId, int feedId, Integer commentRefId, String comment)
            throws ServiceException {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        userInfo = userInfoService.getInfoByUk(userInfo);
        if (userInfo == null) {
            return new ApiRespWrapper<>(-1, "未知的用户账号.");
        }
        FeedInfo feedInfo = feedInfoService.getData(feedId);
        if (feedInfo == null) {
            return new ApiRespWrapper<>(-1, "未知的Feed记录.");
        }
        FeedCommentInfo g = new FeedCommentInfo();
        g.setComment(comment);
        g.setFeedId(feedId);
        g.setUserId(getUserId(openId));
        g.setCommentRefId(commentRefId);
        feedCommentInfoService.addData(g);
        afterModifyComment(true, userInfo.getId(), feedInfo.getSpaceId(), feedId);
        return new ApiRespWrapper<Integer>(g.getId());
    }

    private void afterModifyComment(boolean add, int userId, int spaceId, int feedId) {
        if (add) {
            feedStatInfoService.incrComments(feedId);
            userSpaceInteractionInfoService.incrComments(userId, spaceId);
        } else {
            feedStatInfoService.decrComments(feedId);
            userSpaceInteractionInfoService.decrComments(userId, spaceId);
        }

    }

    private int getUserId(String openId) throws ServiceException {
        UserInfo g = new UserInfo();
        g.setOpenId(openId);
        int id = userInfoService.getIdByUk(g);
        if (id <= 0) {
            throw ServiceException.getParameterException("Invalid open id");
        }
        return id;
    }

    @Override
    public ApiRespWrapper<Boolean> likeFeed(String openId, int feedId, int status) throws ServiceException {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        userInfo = userInfoService.getInfoByUk(userInfo);
        if (userInfo == null) {
            return new ApiRespWrapper<>(-1, "未知的用户账号.");
        }
        FeedInfo feedInfo = feedInfoService.getData(feedId);
        if (feedInfo == null) {
            return new ApiRespWrapper<>(-1, "未知的用户记录.");
        }
        FeedLikeInfo g = new FeedLikeInfo();
        g.setFeedId(feedId);
        g.setUserId(userInfo.getId());
        g.setStatus(status);
        feedLikeInfoService.addData(g);
        if (status == FeedInfo.STATUS_OK) {
            feedStatInfoService.incrLikes(feedId);
            userSpaceInteractionInfoService.incrLikes(userInfo.getId(), feedInfo.getSpaceId());
        } else {
            feedStatInfoService.decrLikes(feedId);
            userSpaceInteractionInfoService.decrLikes(userInfo.getId(), feedInfo.getSpaceId());
        }
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<Boolean> leaveSpace(int userId, int spaceId) throws ServiceException {
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null) {
            return new ApiRespWrapper<Boolean>(-1, "错误的空间编号");
        }
        if (spaceInfo.getUserId() == userId) {
            // owner解散空间
            spaceInfo.setStatus(SpaceInfo.STATUS_DEL);
            spaceInfoService.modifyStatus(spaceInfo);
            userSpaceRelationshipInfoService.modifyStatusBySpaceId(spaceId);
            return new ApiRespWrapper<Boolean>(true);
        } else {
            userSpaceRelationshipInfoService.leaveSpace(userId, spaceId);
            userSpaceInteractionInfoService.modifyStatusByUserIdAndSpaceId(userId, spaceId);
            spaceStatInfoService.decrOccupants(spaceId);
        }
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<Integer> addSpace(int userId, Integer gender, String name, Date bornDate, int type,
            String icon, String cover, String info) throws ServiceException {
        SpaceInfo g = new SpaceInfo();
        g.setName(name);
        g.setBornDate(bornDate);
        g.setType(type);
        g.setGender(gender);
        g.setIcon(icon);
        g.setCover(cover);
        g.setInfo(info);
        g.setUserId(userId);
        spaceInfoService.addData(g);
        afterAddSpace(g);
        return new ApiRespWrapper<Integer>(g.getId());
    }

    private void afterAddSpace(SpaceInfo g) {
        // 自动生成一条secert信息
        spaceSecertInfoService.resetSecert(g.getId());
        UserSpaceRelationshipInfo userSpaceRelationshipInfo = new UserSpaceRelationshipInfo();
        userSpaceRelationshipInfo.setRelationship(SpaceRelationshipEnum.OWNER.getFlag());
        userSpaceRelationshipInfo.setSpaceId(g.getId());
        userSpaceRelationshipInfo.setUserId(g.getUserId());
        userSpaceRelationshipInfoService.addData(userSpaceRelationshipInfo);
        SpaceStatInfo spaceStatInfo = new SpaceStatInfo();
        spaceStatInfo.setId(g.getId());
        spaceStatInfo.setOccupants(1);
        spaceStatInfoService.addData(spaceStatInfo);
    }

    @Override
    public ApiRespWrapper<Boolean> deleteSpace(int userId, int spaceId) throws ServiceException {
        // permission
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null) {
            return new ApiRespWrapper<>(-1, "未知的数据", false);
        }
        if (spaceInfo.getUserId() != userId) {
            return new ApiRespWrapper<>(-1, "你没有删除当前记录的权限", false);
        }
        spaceInfo.setStatus(StatusType.STATUS_DEL.getStatus());
        spaceInfoService.modifyStatus(spaceInfo);
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<Boolean> editSpace(String openId, int spaceId, String name, Date born, String info)
            throws ServiceException {
        spaceInfoService.modifyInfo(spaceId, name, born, info);
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<Boolean> editSpaceIcon(String openId, int spaceId, String icon) throws ServiceException {
        spaceInfoService.modifyIcon(spaceId, icon);
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<Boolean> editSpaceCover(String openId, int spaceId, String cover) throws ServiceException {
        spaceInfoService.modifyCover(spaceId, cover);
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<Integer> joinSpace(String openId, String secert, String fromOpenId, int spaceId)
            throws ServiceException {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        int userId = userInfoService.getIdByUk(userInfo);
        if (userId <= -1) {
            return new ApiRespWrapper<Integer>(-2, "不存在用户Id", null);
        }
        if (StringUtils.isEmpty(secert)) {
            SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
            if (spaceInfo == null) {
                return new ApiRespWrapper<Integer>(-2, "不存在的空间", null);
            }
            UserInfo g = new UserInfo();
            g.setOpenId(fromOpenId);
            int spaceOwnerId = userInfoService.getIdByUk(g);
            if (spaceOwnerId <= -1) {
                return new ApiRespWrapper<Integer>(-2, "不存在空间用户Id", null);
            }
            if (spaceInfo.getUserId() != spaceOwnerId) {
                return new ApiRespWrapper<Integer>(-2, "当前用户并未创建此空间", null);
            }
        } else {
            SpaceSecertInfo g = new SpaceSecertInfo();
            g.setSecert(secert);
            g = spaceSecertInfoService.getInfoByUk(g);
            if (g == null) {
                return new ApiRespWrapper<>(-2, "邀请码无效", null);
            }
            spaceId = g.getSpaceId();
        }
        UserSpaceRelationshipInfo userSpaceRelationshipInfo = new UserSpaceRelationshipInfo();
        userSpaceRelationshipInfo.setUserId(userId);
        userSpaceRelationshipInfo.setSpaceId(spaceId);
        userSpaceRelationshipInfo.setRelationship(SpaceRelationshipEnum.OCCUPANTS.getFlag());
        userSpaceRelationshipInfoService.addData(userSpaceRelationshipInfo);
        return new ApiRespWrapper<Integer>(spaceId);
    }

    @Override
    public ApiRespWrapper<Boolean> joinedSpace(int userId, int spaceId) throws ServiceException {
        return new ApiRespWrapper<Boolean>(userSpaceRelationshipInfoService.joinedSpace(spaceId, userId));
    }

    @Override
    public ApiRespWrapper<String> secertSpace(String openId, int spaceId) throws ServiceException {
        // space owner open id
        int userId = getUserId(openId);
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null || spaceInfo.getUserId() != userId) {
            return new ApiRespWrapper<>(-1, "No permission");
        }
        return new ApiRespWrapper<String>(spaceSecertInfoService.resetSecert(spaceId));
    }

    // 只有评论者和owner才有权限删除
    @Override
    public ApiRespWrapper<Boolean> deleteFeedComment(int userId, int feedId, int commentId) throws ServiceException {
        FeedCommentInfo feedCommentInfo = feedCommentInfoService.getData(commentId);
        if (feedCommentInfo == null) {
            return new ApiRespWrapper<>(-1, "未知的评论记录", false);
        }
        if (feedCommentInfo.getFeedId() != feedId) {
            return new ApiRespWrapper<>(-1, "评论不属于当前Feed", false);
        }
        FeedInfo feedInfo = feedInfoService.getData(feedId);
        if (feedInfo == null) {
            return new ApiRespWrapper<>(-1, "未知的记录", false);
        }
        // self or feed owner
        if (feedCommentInfo.getUserId() == userId || feedInfo.getUserId() == userId) {
            log.warn("Feed comment :" + commentId + " delete by:" + userId);
            feedCommentInfo.setStatus(FeedCommentInfo.STATUS_DEL);
            this.feedCommentInfoService.modifyStatus(feedCommentInfo);
            afterModifyComment(false, userId, feedInfo.getSpaceId(), feedId);
            return new ApiRespWrapper<Boolean>(true);
        }
        return new ApiRespWrapper<>(-1, "你没有删除当前评论的权限.", false);
    }

    @Override
    public ApiRespWrapper<SpaceUserInteractionInfoResp> getSpaceUserInteractionInfo(String openId, String interOpenId,
            int spaceId) throws ServiceException {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        userInfo = userInfoService.getInfoByUk(userInfo);
        if (userInfo == null) {
            return new ApiRespWrapper<SpaceUserInteractionInfoResp>(-1, "未知的用户账号.");
        }

        UserInfo interUserInfo = new UserInfo();
        interUserInfo.setOpenId(interOpenId);
        interUserInfo = userInfoService.getInfoByUk(interUserInfo);
        if (interUserInfo == null) {
            return new ApiRespWrapper<SpaceUserInteractionInfoResp>(-1, "未知的用户账号.");
        }
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null) {
            return new ApiRespWrapper<SpaceUserInteractionInfoResp>(-1, "未知的空间.");
        }
        UserSpaceInteractionInfo userSpaceInteractionInfo = new UserSpaceInteractionInfo();
        userSpaceInteractionInfo.setUserId(interUserInfo.getId());
        userSpaceInteractionInfo.setSpaceId(spaceId);
        userSpaceInteractionInfo = userSpaceInteractionInfoService.getInfoByUk(userSpaceInteractionInfo);
        return new ApiRespWrapper<SpaceUserInteractionInfoResp>(new SpaceUserInteractionInfoResp(interUserInfo,
                userSpaceInteractionInfo, userInfo));
    }

    // //////////以下方法暂时不需要实现
    // ////////以下方法暂时不需要实现
    // ////////以下方法暂时不需要实现

    @Override
    public ApiRespWrapper<SpaceFeedResp> getFeedDetail(String openId, int feedId) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<SpaceFeedCommentListResp>> listFeedComment(String openId, int feedId,
            Integer headCommentId, Integer start, Integer size) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }
}
