// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.core.utils.DateUtils;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.dreambox.web.utils.CollectionUtils;
import com.jmcxclub.dream.family.dto.ActivityFinishEnum;
import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.dto.ActivityPrizeInfo;
import com.jmcxclub.dream.family.dto.ActivityUserPrizeInfo;
import com.jmcxclub.dream.family.dto.ActivityVoteDetailInfo;
import com.jmcxclub.dream.family.dto.ActivityVoteStatInfo;
import com.jmcxclub.dream.family.dto.ActivityWorksExampleInfo;
import com.jmcxclub.dream.family.dto.ActivityWorksInfo;
import com.jmcxclub.dream.family.dto.ActivityWorksInfoEnum;
import com.jmcxclub.dream.family.dto.FeedInfo;
import com.jmcxclub.dream.family.dto.FeedTypeEnum;
import com.jmcxclub.dream.family.dto.PrizeInfo;
import com.jmcxclub.dream.family.model.ActivityInfoResp;
import com.jmcxclub.dream.family.model.ActivityWorksResp;
import com.jmcxclub.dream.family.model.DiscoveryListResp;
import com.jmcxclub.dream.family.model.UploadFileSaveResp;
import com.jmcxclub.dream.family.service.ActivityInfoService;
import com.jmcxclub.dream.family.service.ActivityPrizeInfoService;
import com.jmcxclub.dream.family.service.ActivityPrizeInfoService.ActivityPrizeInfoSortedSetCacheFilter;
import com.jmcxclub.dream.family.service.ActivityUserPrizeInfoService;
import com.jmcxclub.dream.family.service.ActivityVoteDetailInfoService;
import com.jmcxclub.dream.family.service.ActivityVoteStatInfoService;
import com.jmcxclub.dream.family.service.ActivityVoteStatInfoService.ActivityVoteStatInfoSortedListCacheFilter;
import com.jmcxclub.dream.family.service.ActivityWorksExampleInfoService;
import com.jmcxclub.dream.family.service.ActivityWorksInfoService;
import com.jmcxclub.dream.family.service.DiscoveryService;
import com.jmcxclub.dream.family.service.FeedInfoService;
import com.jmcxclub.dream.family.service.ImgService;
import com.jmcxclub.dream.family.service.PrizeInfoService;

/**
 * 发现tab页数据服务类
 * 
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Service("discoveryService")
public class DiscoveryServiceImpl implements DiscoveryService {
    @Autowired
    private ActivityInfoService activityInfoService;
    @Autowired
    private ActivityPrizeInfoService activityPrizeInfoService;
    @Autowired
    private ActivityUserPrizeInfoService activityUserPrizeInfoService;
    @Autowired
    private ActivityWorksInfoService activityWorksInfoService;
    @Autowired
    private ActivityWorksExampleInfoService activityWorksExampleInfoService;
    @Autowired
    private ActivityVoteStatInfoService activityVoteStatInfoService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private ActivityVoteDetailInfoService activityVoteDetailInfoService;
    @Autowired
    private FeedInfoService feedInfoService;
    @Autowired
    private PrizeInfoService prizeInfoService;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public ApiRespWrapper<ListWrapResp<DiscoveryListResp>> listDiscovery(String openId, Integer start, Integer size)
            throws ServiceException {
        StartSizeCacheFilter filter = new StartSizeCacheFilter();
        filter.setStart(start);
        filter.setSize(size);
        ListWrapResp<ActivityInfo> infos = activityInfoService.listInfo(filter);
        if (infos == null || CollectionUtils.emptyOrNull(infos.getResultList())) {
            return new ApiRespWrapper<ListWrapResp<DiscoveryListResp>>(new ListWrapResp<DiscoveryListResp>(
                    new ArrayList<DiscoveryListResp>()));
        }
        List<DiscoveryListResp> datas = new ArrayList<DiscoveryListResp>();
        ActivityVoteStatInfoSortedListCacheFilter statCacheFilter = new ActivityVoteStatInfoSortedListCacheFilter();
        for (ActivityInfo info : infos.getResultList()) {
            long participates = activityVoteStatInfoService.count(statCacheFilter);
            datas.add(new DiscoveryListResp(info, participates));
        }
        return new ApiRespWrapper<ListWrapResp<DiscoveryListResp>>(new ListWrapResp<DiscoveryListResp>(
                infos.getTotalCount(), datas, infos.isMore(), infos.getNext()));
    }

    @Override
    public ApiRespWrapper<ActivityInfoResp> getActivity(int userId, int id) {
        ActivityInfo info = activityInfoService.getData(id);
        List<ActivityWorksExampleInfo> examples = activityWorksExampleInfoService.listInfo(id);
        ActivityWorksInfo activityWorksInfo = new ActivityWorksInfo();
        activityWorksInfo.setActivityId(id);
        activityWorksInfo.setUserId(userId);
        int worksId = activityWorksInfoService.getIdByUk(activityWorksInfo);
        ListWrapResp<ActivityPrizeInfo> activityPrizeInfoResp = activityPrizeInfoService
                .listInfo(new ActivityPrizeInfoSortedSetCacheFilter(id));
        List<ActivityPrizeInfo> activityPrizeInfos = activityPrizeInfoResp == null ? new ArrayList<ActivityPrizeInfo>()
                : activityPrizeInfoResp.getResultList();
        List<Integer> prizeIds = new ArrayList<Integer>();
        for (ActivityPrizeInfo activityPrizeInfo : activityPrizeInfos) {
            prizeIds.add(activityPrizeInfo.getPrizeId());
        }
        List<PrizeInfo> prizeInfos = prizeInfoService.getData(prizeIds);
        List<ActivityUserPrizeInfo> userPrizes = null;
        List<UserInfo> userInfos = null;
        if (info.getFinish() == ActivityFinishEnum.FINISH.getFinish()) {
            userPrizes = activityUserPrizeInfoService.listInfo(info.getId());
            List<Integer> userIds = new ArrayList<Integer>();
            for (ActivityUserPrizeInfo activityUserPrizeInfo : userPrizes) {
                userIds.add(activityUserPrizeInfo.getUserId());
            }
            userInfoService.getData(userIds);
        }
        return new ApiRespWrapper<ActivityInfoResp>(new ActivityInfoResp(info, examples, activityPrizeInfos,
                prizeInfos, worksId, userPrizes, userInfos));
    }

    @Override
    public ApiRespWrapper<Boolean> applyActivity(int userId, int activityId, int feedId) {
        FeedInfo feedInfo = feedInfoService.getData(feedId);
        if (feedInfo == null) {
            return new ApiRespWrapper<Boolean>(-1, "未知的参赛作品", false);
        }
        if (feedInfo.getUserId() != userId) {
            return new ApiRespWrapper<Boolean>(-1, "不能以此作品参赛", false);
        }
        int type = adapationType(feedInfo.getType());
        return applyActivity(userId, activityId, feedInfo.getContent(), feedInfo.getTitle(), feedInfo.getContent(),
                type);
    }

    private int adapationType(int type) {
        if (type == FeedTypeEnum.VIDEO.getType()) {
            return ActivityWorksInfoEnum.VIDEO.getType();
        }
        if (type == FeedTypeEnum.AUDIO.getType()) {
            return ActivityWorksInfoEnum.AUDIO.getType();
        }
        return ActivityWorksInfoEnum.NORMAL.getType();
    }

    @Override
    public ApiRespWrapper<Boolean> applyActivity(int userId, int activityId, MultipartFile image, String solgan,
            String desc) {
        ActivityWorksInfo g = new ActivityWorksInfo();
        g.setActivityId(activityId);
        g.setUserId(userId);
        // 用户是否已经参与
        g = activityWorksInfoService.getInfoByUk(g);
        if (g != null) {
            return new ApiRespWrapper<Boolean>(-1, "您已参与", false);
        }
        String cover = null;
        if (image != null && !image.isEmpty()) {
            UploadFileSaveResp resp = imgService.saveActivityWorksImg(image, userId);
            if (resp.isSaved()) {
                cover = resp.getUrlPath();
            } else {
                return new ApiRespWrapper<Boolean>(-1, "存储作品图片失败", false);
            }
        }
        return applyActivity(userId, activityId, cover, solgan, desc, ActivityWorksInfoEnum.NORMAL.getType());
    }

    private ApiRespWrapper<Boolean> applyActivity(int userId, int activityId, String cover, String solgan, String desc,
            int type) {
        ActivityWorksInfo g = new ActivityWorksInfo();
        g.setActivityId(activityId);
        g.setCover(cover);
        g.setDesc(desc);
        g.setSolgan(solgan);
        g.setType(type);
        g.setUserId(userId);
        try {
            activityWorksInfoService.addData(g);
        } catch (ServiceException e) {
            if (e.getErrorCode() == ServiceException.ERR_CODE_SQL_DATA_DUPLICATE) {
                return new ApiRespWrapper<>(-1, "您已参与", false);
            }
        }
        afterApplyActivity(g);
        return new ApiRespWrapper<Boolean>(true);
    }

    private void afterApplyActivity(ActivityWorksInfo g) {
        ActivityVoteStatInfo activityVoteStatInfo = new ActivityVoteStatInfo();
        activityVoteStatInfo.setActivityId(g.getActivityId());
        activityVoteStatInfo.setId(g.getId());
        activityVoteStatInfoService.addData(activityVoteStatInfo);
    }

    @Override
    public ApiRespWrapper<Boolean> voteActivity(int userId, int activityId, int worksId, String ip) {
        //
        ActivityWorksInfo activityWorksInfo = activityWorksInfoService.getData(worksId);
        if (activityWorksInfo == null) {
            return new ApiRespWrapper<>(-1, "错误的投票选项", false);
        }
        if (activityWorksInfo.getActivityId() != activityId) {
            return new ApiRespWrapper<>(-1, "未对应的投票活动", false);
        }
        Date date = new Date();
        ActivityVoteDetailInfo activityVoteDetailInfo = new ActivityVoteDetailInfo();
        activityVoteDetailInfo.setIp(ip);
        activityVoteDetailInfo.setUserId(userId);
        activityVoteDetailInfo.setVoteDate(DateUtils.getDateYMDIntValue(date));
        activityVoteDetailInfo.setVoteTime(date);
        activityVoteDetailInfo.setWorksId(worksId);
        boolean vote = activityVoteDetailInfoService.addOrIgnoreData(activityVoteDetailInfo);
        if (vote) {
            activityVoteStatInfoService.incr(worksId, activityId);
        } else {
            return new ApiRespWrapper<Boolean>(-1, "今天已投票", false);
        }
        return new ApiRespWrapper<Boolean>(true);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ActivityWorksResp>> listActivityWorks(UserInfo userInfo, Integer activityId,
            String findKey, Integer voteWorksId, Integer start, Integer size) throws ServiceException {
        if (!StringUtils.isEmpty(findKey)) {
            return handleFindActivityWorks(findKey);
        }
        ListWrapResp<ActivityVoteStatInfo> datas = activityVoteStatInfoService
                .listInfo(new ActivityVoteStatInfoSortedListCacheFilter(activityId, start, size));
        ActivityWorksInfo curUserActiviteWorksInfo = null;
        ActivityWorksInfo voteActiviteWorksInfo = null;
        ActivityVoteStatInfo curUserActivityVoteStatInfo = null;
        ActivityVoteStatInfo voteWorkActivityVoteStatInfo = null;
        int curUserActiviteWorksRank = 0;
        int voteActiviteWorksRank = 0;
        if (start == 0) {
            ActivityWorksInfo activiteWorksInfo = new ActivityWorksInfo();
            activiteWorksInfo.setUserId(userInfo.getId());
            activiteWorksInfo.setActivityId(activityId);
            curUserActiviteWorksInfo = activityWorksInfoService.getInfoByUk(activiteWorksInfo);
            if (curUserActiviteWorksInfo != null) {
                // 当前作者作品
                curUserActivityVoteStatInfo = activityVoteStatInfoService.getData(curUserActiviteWorksInfo.getId());
                curUserActiviteWorksRank = activityVoteStatInfoService.rank(activityId.intValue(),
                        curUserActiviteWorksInfo.getId());
            }

            if (voteWorksId != null
                    && voteWorksId.intValue() > 0
                    && (curUserActiviteWorksInfo == null || (curUserActiviteWorksInfo != null && curUserActiviteWorksInfo
                            .getId() != voteWorksId))) {
                // 当前投票作品
                voteActiviteWorksInfo = activityWorksInfoService.getData(voteWorksId.intValue());
                voteWorkActivityVoteStatInfo = activityVoteStatInfoService.getData(voteWorksId.intValue());
                voteActiviteWorksRank = activityVoteStatInfoService.rank(activityId.intValue(), voteWorksId.intValue());
            }
        }
        List<Integer> worksIds = new ArrayList<Integer>();
        for (ActivityVoteStatInfo activityVoteStatInfo : datas.getResultList()) {
            worksIds.add(activityVoteStatInfo.getId());
        }
        int addtional = 0;
        List<ActivityWorksInfo> activityWorksInfos = activityWorksInfoService.getData(worksIds);
        if (start == 0 && curUserActiviteWorksInfo != null) {
            activityWorksInfos.add(0, curUserActiviteWorksInfo);
            addtional++;
        }
        if (start == 0 && voteActiviteWorksInfo != null) {
            activityWorksInfos.add(0, voteActiviteWorksInfo);
            addtional++;
        }
        List<ActivityWorksResp> values = new ArrayList<ActivityWorksResp>();
        boolean voteWorksAdd = false;
        boolean userWorksAdd = false;
        for (ActivityWorksInfo activityWorksInfo : activityWorksInfos) {
            if (!voteWorksAdd && voteActiviteWorksInfo != null
                    && activityWorksInfo.getId() == voteActiviteWorksInfo.getId()
                    && voteWorkActivityVoteStatInfo != null) {
                values.add(new ActivityWorksResp(activityWorksInfo, voteActiviteWorksRank, voteWorkActivityVoteStatInfo
                        .getVotes()));
                voteWorksAdd = true;
                continue;
            }
            if (!userWorksAdd && curUserActiviteWorksInfo != null
                    && activityWorksInfo.getId() == curUserActiviteWorksInfo.getId()
                    && curUserActivityVoteStatInfo != null) {
                values.add(new ActivityWorksResp(activityWorksInfo, curUserActiviteWorksRank,
                        curUserActivityVoteStatInfo.getVotes()));
                userWorksAdd = true;
                continue;
            }
            if (curUserActiviteWorksInfo != null && activityWorksInfo.getId() == curUserActiviteWorksInfo.getId()) {
                addtional--;
                continue;
            }
            if (voteActiviteWorksInfo != null && activityWorksInfo.getId() == voteActiviteWorksInfo.getId()) {
                addtional--;
                continue;
            }
            for (ActivityVoteStatInfo activityVoteStatInfo : datas.getResultList()) {
                if (activityVoteStatInfo.getId() == activityWorksInfo.getId()) {
                    int rank = activityVoteStatInfoService.rank(activityVoteStatInfo.getActivityId(),
                            activityVoteStatInfo.getId());
                    values.add(new ActivityWorksResp(activityWorksInfo, rank, activityVoteStatInfo.getVotes()));
                    break;
                }
            }
        }
        boolean more = datas.isMore() && datas.getTotalCount() > start + size + addtional;
        return new ApiRespWrapper<ListWrapResp<ActivityWorksResp>>(new ListWrapResp<ActivityWorksResp>(
                datas.getTotalCount(), values, more, datas.getNext()));
    }

    private ApiRespWrapper<ListWrapResp<ActivityWorksResp>> handleFindActivityWorks(String findKey) {
        try {
            int findWorksId = Integer.parseInt(findKey);
            ActivityWorksInfo activityWorksInfo = activityWorksInfoService.getData(findWorksId);
            if (activityWorksInfo != null) {
                ActivityVoteStatInfo activityVoteStatInfo = activityVoteStatInfoService.getData(findWorksId);
                int rank = activityVoteStatInfoService.rank(activityWorksInfo.getActivityId(), findWorksId);
                List<ActivityWorksResp> resultList = new ArrayList<ActivityWorksResp>();
                resultList.add(new ActivityWorksResp(activityWorksInfo, rank, activityVoteStatInfo == null ? 0
                        : activityVoteStatInfo.getVotes()));
                return new ApiRespWrapper<ListWrapResp<ActivityWorksResp>>(new ListWrapResp<ActivityWorksResp>(
                        resultList));
            }
        } catch (Exception e) {
        }
        return new ApiRespWrapper<ListWrapResp<ActivityWorksResp>>(null);
    }
}
