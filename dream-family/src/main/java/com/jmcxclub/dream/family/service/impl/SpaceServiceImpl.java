// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreambox.core.StatusType;
import com.dreambox.core.cache.CacheFilter.IdStartSizeCacheFilter;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.dreambox.web.utils.CollectionUtils;
import com.jmcxclub.dream.family.dto.FeedCommentInfo;
import com.jmcxclub.dream.family.dto.FeedInfo;
import com.jmcxclub.dream.family.dto.FeedLikeInfo;
import com.jmcxclub.dream.family.dto.SpaceInfo;
import com.jmcxclub.dream.family.dto.SpaceRelationshipEnum;
import com.jmcxclub.dream.family.dto.SpaceSecertInfo;
import com.jmcxclub.dream.family.dto.SpaceStatInfo;
import com.jmcxclub.dream.family.dto.UserSpaceRelationshipInfo;
import com.jmcxclub.dream.family.model.OccupantFootprintResp;
import com.jmcxclub.dream.family.model.SpaceFeedCommentListResp;
import com.jmcxclub.dream.family.model.SpaceFeedListResp;
import com.jmcxclub.dream.family.model.SpaceFeedResp;
import com.jmcxclub.dream.family.model.SpaceInfoResp;
import com.jmcxclub.dream.family.model.SpaceListResp;
import com.jmcxclub.dream.family.model.UserIdStartSizeCacheFilter;
import com.jmcxclub.dream.family.service.FeedCommentInfoService;
import com.jmcxclub.dream.family.service.FeedCommentInfoService.CommentSortedSetCacheFilter;
import com.jmcxclub.dream.family.service.FeedInfoService;
import com.jmcxclub.dream.family.service.FeedLikeInfoService;
import com.jmcxclub.dream.family.service.FeedLikeInfoService.LikeInfoIdSortedSetCacheFilter;
import com.jmcxclub.dream.family.service.FeedStatInfoService;
import com.jmcxclub.dream.family.service.SpaceInfoService;
import com.jmcxclub.dream.family.service.SpaceSecertInfoService;
import com.jmcxclub.dream.family.service.SpaceService;
import com.jmcxclub.dream.family.service.SpaceStatInfoService;
import com.jmcxclub.dream.family.service.UserSpaceRelationshipInfoService;

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
        UserIdStartSizeCacheFilter filter = new UserIdStartSizeCacheFilter(userId, start, size);
        ListWrapResp<UserSpaceRelationshipInfo> spaceInfosResp = userSpaceRelationshipInfoService.listInfo(filter);
        List<Integer> spaceIds = new ArrayList<Integer>();
        for (UserSpaceRelationshipInfo spaceInfo : spaceInfosResp.getResultList()) {
            spaceIds.add(spaceInfo.getSpaceId());
        }
        List<SpaceInfo> infos = spaceInfoService.getData(spaceIds);
        List<SpaceStatInfo> stats = spaceStatInfoService.getData(spaceIds);
        List<SpaceListResp> datas = buildSpaceListResps(infos, stats);
        ListWrapResp<SpaceListResp> data = new ListWrapResp<SpaceListResp>(spaceInfosResp.getTotalCount(), datas,
                spaceInfosResp.isMore(), spaceInfosResp.getNext());
        return new ApiRespWrapper<ListWrapResp<SpaceListResp>>(data);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<OccupantFootprintResp>> listSpaceOccupantFootprint(String openId, int spaceId)
            throws ServiceException {
        IdStartSizeCacheFilter filter = new IdStartSizeCacheFilter();
        filter.setId(spaceId);
        ListWrapResp<UserSpaceRelationshipInfo> infos = userSpaceRelationshipInfoService.listInfo(filter);
        List<OccupantFootprintResp> resultList = new ArrayList<OccupantFootprintResp>();
        List<Integer> userIds = new ArrayList<Integer>();
        for (UserSpaceRelationshipInfo info : infos.getResultList()) {
            userIds.add(info.getUserId());
        }
        List<UserInfo> userInfos = userInfoService.getData(userIds);
        for (UserSpaceRelationshipInfo info : infos.getResultList()) {
            UserInfo curUser = null;
            for (UserInfo userInfo : userInfos) {
                if (userInfo.getId() == info.getUserId()) {
                    curUser = userInfo;
                }
            }
            resultList.add(new OccupantFootprintResp(info, curUser));
        }
        ListWrapResp<OccupantFootprintResp> datas = new ListWrapResp<OccupantFootprintResp>(resultList);
        return new ApiRespWrapper<ListWrapResp<OccupantFootprintResp>>(datas);
    }

    @Override
    public ApiRespWrapper<SpaceInfoResp> getSpaceInfo(String openId, int spaceId) throws ServiceException {
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null) {
            return new ApiRespWrapper<SpaceInfoResp>(-1, "Illegal space id.");
        }
        SpaceStatInfo stat = spaceStatInfoService.getData(spaceId);

        IdStartSizeCacheFilter filter = new IdStartSizeCacheFilter();
        filter.setId(spaceId);
        filter.setSize(5);
        ListWrapResp<UserSpaceRelationshipInfo> occupants = userSpaceRelationshipInfoService.listInfo(filter);
        List<Integer> userIds = new ArrayList<Integer>();
        for (UserSpaceRelationshipInfo userSpaceRelationshipInfo : occupants.getResultList()) {
            userIds.add(userSpaceRelationshipInfo.getUserId());
        }
        List<UserInfo> userInfos = userInfoService.getData(userIds);
        return new ApiRespWrapper<SpaceInfoResp>(buildSpaceInfoResp(spaceInfo, stat, userInfos));
    }

    private SpaceInfoResp buildSpaceInfoResp(SpaceInfo spaceInfo, SpaceStatInfo stat, List<UserInfo> userInfos) {
        return new SpaceInfoResp(spaceInfo, stat, userInfos);
    }

    private List<SpaceListResp> buildSpaceListResps(List<SpaceInfo> infos, List<SpaceStatInfo> stats) {
        List<SpaceListResp> datas = new ArrayList<SpaceListResp>();
        for (SpaceInfo spaceInfo : infos) {
            SpaceStatInfo curStat = null;
            for (SpaceStatInfo stat : stats) {
                if (spaceInfo.getId() == stat.getId()) {
                    curStat = stat;
                }
            }
            datas.add(new SpaceListResp(spaceInfo, curStat));
        }
        return datas;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> listSpaceFeed(String openId, int spaceId, int start, int size)
            throws ServiceException {
        SpaceInfo spaceInfo = spaceInfoService.getData(spaceId);
        if (spaceInfo == null) {
            return new ApiRespWrapper<ListWrapResp<SpaceFeedListResp>>(-1, "Illegal space id", null);
        }
        IdStartSizeCacheFilter filter = new IdStartSizeCacheFilter();
        filter.setId(spaceId);
        filter.setStart(start);
        filter.setSize(size);
        ListWrapResp<FeedInfo> infos = feedInfoService.listInfo(filter);
        spaceStatInfoService.incrViews(spaceId);
        return buildFeedListResp(spaceInfo, infos);
    }

    private ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> buildFeedListResp(SpaceInfo spaceInfo,
            ListWrapResp<FeedInfo> infos) {
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
            feedIdLikeInfoMap.put(info.getId(), likes);
            CommentSortedSetCacheFilter commentSortedSetCacheFilter = new CommentSortedSetCacheFilter(null,
                    info.getId(), 0, Integer.MAX_VALUE);
            ListWrapResp<FeedCommentInfo> comments = this.feedCommentInfoService.listInfo(commentSortedSetCacheFilter);
            feedIdCommenteInfoMap.put(info.getId(), comments);

        }
        for (Entry<Integer, ListWrapResp<FeedCommentInfo>> entry : feedIdCommenteInfoMap.entrySet()) {
            for (FeedCommentInfo comment : entry.getValue().getResultList()) {
                if (!userIds.contains(comment.getUserId())) {
                    userIds.add(comment.getUserId());
                }
            }
        }
        for (Entry<Integer, ListWrapResp<FeedLikeInfo>> entry : feedIdLikeInfoMap.entrySet()) {
            for (FeedLikeInfo likeInfo : entry.getValue().getResultList()) {
                if (!userIds.contains(likeInfo.getUserId())) {
                    userIds.add(likeInfo.getUserId());
                }
            }
        }
        // GET user'info
        List<UserInfo> userInfos = this.userInfoService.getData(userIds);
        Map<Integer, UserInfo> userIdInfoMap = CollectionUtils.transformToMap(userInfos);
        return buildFeedListResp(spaceInfo, infos, feedIdCommenteInfoMap, feedIdLikeInfoMap, userIdInfoMap);
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
    private ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> buildFeedListResp(SpaceInfo spaceInfo,
            ListWrapResp<FeedInfo> infos, Map<Integer, ListWrapResp<FeedCommentInfo>> feedIdCommenteInfoMap,
            Map<Integer, ListWrapResp<FeedLikeInfo>> feedIdLikeInfoMap, Map<Integer, UserInfo> userIdInfoMap) {
        // TODO
        return null;
    }

    @Override
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
        feedInfoService.addData(g);
        try {
            spaceStatInfoService.incrRecords(spaceId);
        } catch (ServiceException e) {
            log.error("Incr space records failed. SpaceId:" + spaceId + ", Errmsg:" + e.getMessage());
        }
        return new ApiRespWrapper<Integer>(g.getId());
    }

    @Override
    public ApiRespWrapper<Integer> addFeedComment(String openId, int feedId, Integer commentRefId, String comment)
            throws ServiceException {
        FeedCommentInfo g = new FeedCommentInfo();
        g.setComment(comment);
        g.setFeedId(feedId);
        g.setUserId(getUserId(openId));
        g.setCommentRefId(commentRefId);
        feedCommentInfoService.addData(g);
        feedStatInfoService.incrComments(feedId);
        return new ApiRespWrapper<Integer>(g.getId());
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
        FeedLikeInfo g = new FeedLikeInfo();
        g.setFeedId(feedId);
        g.setUserId(getUserId(openId));
        g.setStatus(status);
        feedLikeInfoService.addData(g);
        if (status == FeedInfo.STATUS_OK) {
            feedStatInfoService.incrLikes(feedId);
        } else {
            feedStatInfoService.decrLikes(feedId);
        }
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<Integer> addSpace(String openId, String title, String darlingName, Date darlingBornDate,
            int darlingType, String icon, String cover, String info) throws ServiceException {
        SpaceInfo g = new SpaceInfo();
        g.setTitle(title);
        g.setDarlingName(darlingName);
        g.setDarlingBornDate(darlingBornDate);
        g.setDarlingType(darlingType);
        g.setIcon(icon);
        g.setCover(cover);
        g.setInfo(info);
        spaceInfoService.addData(g);
        return new ApiRespWrapper<Integer>(g.getId());
    }

    @Override
    public ApiRespWrapper<Boolean> deleteSpace(String openId, int spaceId) throws ServiceException {
        SpaceInfo g = new SpaceInfo();
        g.setId(spaceId);
        g.setStatus(StatusType.STATUS_DEL.getStatus());
        spaceInfoService.modifyStatus(g);
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<Boolean> editSpace(String openId, int spaceId, String title) throws ServiceException {
        spaceInfoService.modifyInfo(spaceId, title);
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> joinSpace(String openId, String secert)
            throws ServiceException {
        SpaceSecertInfo g = new SpaceSecertInfo();
        g.setSecert(secert);
        g = spaceSecertInfoService.getInfoByUk(g);
        if (g == null) {
            throw ServiceException.getDbDataExceptipn("Secret is expired.");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        int userId = userInfoService.getIdByUk(userInfo);
        if (userId <= -1) {
            throw ServiceException.getParameterException("Invalid open id");
        }
        UserSpaceRelationshipInfo userSpaceRelationshipInfo = new UserSpaceRelationshipInfo();
        userSpaceRelationshipInfo.setUserId(userId);
        userSpaceRelationshipInfo.setSpaceId(g.getSpaceId());
        userSpaceRelationshipInfo.setRelationship(SpaceRelationshipEnum.OCCUPANTS.getFlag());
        userSpaceRelationshipInfoService.addData(userSpaceRelationshipInfo);
        return listSpaceFeed(openId, g.getSpaceId(), 0, 5);
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

    @Override
    public ApiRespWrapper<Boolean> deleteFeedComment(String openId, int feedId, int commentId) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }
}
