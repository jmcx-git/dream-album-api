// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.utils.DateUtils;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.CollectionUtils;
import com.dreambox.web.utils.GsonUtils;
import com.jmcxclub.dream.family.dto.ActivityFinishEnum;
import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.dto.ActivityPrizeInfo;
import com.jmcxclub.dream.family.dto.ActivityUserPrizeInfo;
import com.jmcxclub.dream.family.dto.ActivityVoteStatInfo;
import com.jmcxclub.dream.family.dto.ActivityWorksInfo;
import com.jmcxclub.dream.family.dto.FeedInfo;
import com.jmcxclub.dream.family.dto.FeedInnerPhoto;
import com.jmcxclub.dream.family.dto.PrizeInfo;
import com.jmcxclub.dream.family.dto.SpaceInfo;
import com.jmcxclub.dream.family.dto.SpaceTypeEnum;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;
import com.jmcxclub.dream.family.dto.WikiInfo;
import com.jmcxclub.dream.family.model.ActivityInfoResp;
import com.jmcxclub.dream.family.model.ActivityInfoResp.ActivityPrizeResp;
import com.jmcxclub.dream.family.model.ActivityStepEnum;
import com.jmcxclub.dream.family.model.ActivityWorksResp;
import com.jmcxclub.dream.family.model.DiscoveryListResp;
import com.jmcxclub.dream.family.model.SpaceFeedListResp;
import com.jmcxclub.dream.family.model.UserFeedListResp;
import com.jmcxclub.dream.family.model.UserPrizeResp;
import com.jmcxclub.dream.family.model.WikiPhaseResp;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class ContentDescUtils {
    private static final Logger log = Logger.getLogger(ContentDescUtils.class);
    public static final String JSON_OBJECT_LIST_SPLIT_CHAR = "&";

    public static String decode(String content) {
        if (StringUtils.isNotEmpty(content)) {
            try {
                content = URLDecoder.decode(content, "utf8");
            } catch (UnsupportedEncodingException e) {
            }
        }
        return content;
    }

    public static String buildUserVisitSpaceInfo(UserSpaceInteractionInfo info, int accessUserId) {
        if (info == null) {
            return "未发现其踪迹";
        }
        long curTimeMillis = System.currentTimeMillis();
        long preVisiMillis = info.getUpdateTime().getTime();

        long minutes = (curTimeMillis - preVisiMillis) / 6000;
        String minDesc = "";
        if (accessUserId == info.getUserId() || minutes < 1) {
            minDesc = "正在查看";
        } else {
            long hours = minutes / 60;
            if (hours <= 0) {
                minDesc = minutes + "分钟前来过";
            } else {
                long days = hours / 24;
                if (days <= 0) {
                    minDesc = hours + "小时前来过";
                } else {
                    if (days <= 355) {
                        minDesc = days + "天前来过";
                    } else {
                        minDesc = "1年前来过";
                    }
                }
            }
        }
        return minDesc + "," + "共来过" + info.getViews() + "次";
    }

    public static String buildTimeDesc(FeedInfo feedInfo) {
        long curTimeMillis = System.currentTimeMillis();
        long preVisiMillis = feedInfo.getCreateTime().getTime();
        long minutes = (curTimeMillis - preVisiMillis) / 60000;
        String minDesc = "";
        if (minutes < 1) {
            minDesc = "刚刚发布";
        } else {
            long hours = minutes / 60;
            if (hours <= 0) {
                minDesc = minutes + "分钟前";
            } else {
                long days = hours / 24;
                if (days <= 0) {
                    minDesc = hours + "小时前";
                } else {
                    if (days <= 355) {
                        minDesc = days + "天前";
                    } else {
                        minDesc = "1年前";
                    }
                }
            }
        }
        return minDesc;
    }

    public static String buildEnglishTimeDesc(Date date) {
        return DateUtils.formatStr(date, "dd MMM.yyyy");
    }

    public static void buildActivityInfo(DiscoveryListResp discoveryListResp, ActivityInfo activityInfo) {
        int step = 0;
        String stepDesc = "";
        String startTimeDesc = buildEnglishTimeDesc(activityInfo.getStartDate());
        if (activityInfo.getFinish() == ActivityFinishEnum.FINISH.getFinish()) {
            stepDesc = "已结束，点击查看结果";
            step = ActivityStepEnum.FINISH.getStep();
        } else {
            long curTimeMillis = System.currentTimeMillis();
            long minutes = (curTimeMillis - activityInfo.getStartDate().getTime()) / 60000;
            if (minutes < 0) {
                step = ActivityStepEnum.INIT.getStep();
                minutes = 0 - minutes;
                long hour = minutes / 60;
                if (hour == 0) {
                    stepDesc = minutes + "分钟后盛大开启，敬请期待";
                } else {
                    long day = hour / 24;
                    if (day == 0) {
                        stepDesc = hour + "小时后盛大开启，敬请期待";
                    } else {
                        stepDesc = day + "天后盛大开启，敬请期待";
                    }
                }
            } else {
                long leftSeconds = (activityInfo.getEndDate().getTime() - curTimeMillis) / 1000;
                if (leftSeconds > 0) {
                    step = ActivityStepEnum.ING.getStep();
                    long leftMinutes = leftSeconds / 60;
                    if (leftMinutes == 0) {
                        stepDesc = "距结束仅剩" + leftSeconds + "秒钟";
                    } else {
                        long hour = leftMinutes / 60;
                        if (hour == 0) {
                            stepDesc = "距结束仅剩" + leftMinutes + "分钟";
                        } else {
                            long day = hour / 24;
                            if (day == 0) {
                                stepDesc = "距结束仅剩" + hour + "小时";
                            } else {
                                stepDesc = "距结束" + day + "天";
                            }
                        }
                    }

                } else {
                    stepDesc = "投票结束，正在出奖，敬请等待.";
                    step = ActivityStepEnum.AUDIT.getStep();
                }
            }
        }
        discoveryListResp.setStepDesc(stepDesc);
        discoveryListResp.setStartTimeDesc(startTimeDesc);
        discoveryListResp.setStep(step);
    }

    public static String buildNotice(PrizeInfo prizeInfo, ActivityInfo activityInfo, ActivityWorksInfo activityWorksInfo) {
        // 恭喜您，在***活动中，获得大奖一份，请前往发现，找到活动，获取相关信息，或者通过下方与客房对话图标联系我们。
        return "恭喜您，在【" + activityInfo.getTitle() + "】活动中，获得大奖一份，在微信中搜索客服微信号【Niubihao110】，与之对话，请于2017-02-15 00:00:00前联系客服，获取兑奖详情。在此之后联系的将不再予兑奖，敬请注意。";
    }

    public static String buildNoticeTimeDesc(Date createTime) {
        return DateUtils.formatStr(createTime, DateUtils.YYYYMMDDHHMMSS_FORMAT);
    }

    /**
     * 
     // need build private int step;// see ActivityStepEnum for css style
     * private List<String> contentSections;// 活动段落 private String
     * activityTimeDesc;// 活动时间 private List<ActivityPrizeResp> prizes;
     * 
     * private int stepTime;// 距结束N天中的天 private String stepTimeUnitDesc;//
     * 距结束N天中的N;
     * 
     * @param activityInfoResp
     * @param info
     * @param activityPrizeInfos
     * @param prizeInfos
     * @param prizeUserInfos
     * @param userPrizes
     */
    public static void buildActivityInfoRespOthers(ActivityInfoResp activityInfoResp, ActivityInfo info,
            List<ActivityPrizeInfo> activityPrizeInfos, List<PrizeInfo> prizeInfos,
            List<ActivityUserPrizeInfo> userPrizes, Map<Integer, UserInfo> userMap,
            Map<Integer, ActivityVoteStatInfo> votesMap) {
        int step = 0;
        long stepTime = 0;
        String stepTimeUnit = "";
        String stepDesc = "";
        String bottomStepDesc = "";// 只在step==0,2,3只有值F
        String activityTimeDesc = buildStartEndTimeDesc(info.getStartDate(), info.getEndDate());
        String prizeTimeDesc = buildChineseTimeDesc(info.getPrizeDate());
        String[] contens = info.getContent().split("<br/>");
        if (contens.length == 1) {
            contens = info.getContent().split("\r\n");
        }
        if (contens.length == 1) {
            contens = info.getContent().split("\n");
        }
        List<String> contentSections = Arrays.asList(contens);
        if (info.getFinish() == ActivityFinishEnum.FINISH.getFinish()) {
            //
            stepDesc = "活动已结束";
            bottomStepDesc = "活动已结束-点击查看结果";
            step = ActivityStepEnum.FINISH.getStep();
        } else {
            Date curDate = new Date();
            if (info.getStartDate().after(curDate)) {
                stepDesc = "活动未开始";
                bottomStepDesc = "即将开始，敬请期待";
                step = ActivityStepEnum.INIT.getStep();
            } else if (info.getEndDate().before(curDate)) {
                stepDesc = "计票统计中，即将开奖";
                bottomStepDesc = "计票统计中，即将开奖";
                step = ActivityStepEnum.AUDIT.getStep();
            } else {
                long seconds = (info.getEndDate().getTime() - curDate.getTime()) / 1000;
                if (seconds == 0) {
                    stepDesc = "活动已结束";
                    bottomStepDesc = "计票统计中，即将开奖";
                    step = ActivityStepEnum.FINISH.getStep();
                } else {
                    step = ActivityStepEnum.ING.getStep();
                    long minutes = seconds / 60;
                    if (minutes == 0) {
                        stepTime = seconds;
                        stepTimeUnit = "秒";
                    } else {
                        long hour = minutes / 60;
                        if (hour == 0) {
                            stepTime = minutes;
                            stepTimeUnit = "分钟";
                        } else {
                            long day = hour / 24;
                            if (day == 0) {
                                stepTime = hour;
                                stepTimeUnit = "小时";
                            } else {
                                stepTime = day;
                                stepTimeUnit = "天";
                            }
                        }
                    }
                }
            }
        }

        List<ActivityPrizeResp> prizes = new ArrayList<ActivityPrizeResp>();
        for (ActivityPrizeInfo activityPrizeInfo : activityPrizeInfos) {
            PrizeInfo curPrize = null;
            for (PrizeInfo prizeInfo : prizeInfos) {
                if (activityPrizeInfo.getPrizeId() == prizeInfo.getId()) {
                    curPrize = prizeInfo;
                }
            }
            if (curPrize == null) {
                continue;
            }
            String prizeInfo = curPrize.getTitle() + activityPrizeInfo.getCount() + "个";
            prizes.add(new ActivityPrizeResp(activityPrizeInfo.getRankDesc(), prizeInfo, curPrize.getImg()));
        }

        List<UserPrizeResp> userPrizeResp = null;// 用户中奖信息
        if (CollectionUtils.notEmptyAndNull(userPrizes) && CollectionUtils.notEmptyAndNull(userMap)
                && CollectionUtils.notEmptyAndNull(votesMap)) {
            userPrizeResp = new ArrayList<UserPrizeResp>();

            for (ActivityUserPrizeInfo userPrize : userPrizes) {
                for (ActivityPrizeInfo activityPrizeInfo : activityPrizeInfos) {
                    if (activityPrizeInfo.getId() == userPrize.getActivityPrizeId()) {
                        UserInfo curUser = userMap.get(userPrize.getUserId());
                        ActivityVoteStatInfo statInfo = votesMap.get(userPrize.getWorksId());
                        if (curUser == null || statInfo == null) {
                            break;
                        }
                        UserPrizeResp addResp = new UserPrizeResp();
                        addResp.setRank(activityPrizeInfo.getRank());
                        addResp.setVote(statInfo.getVotes());
                        addResp.setName(curUser.getNickName());
                        userPrizeResp.add(addResp);
                        break;
                    }
                }
            }
        }
        if (CollectionUtils.notEmptyAndNull(userPrizeResp)) {
            Collections.sort(userPrizeResp, new Comparator<UserPrizeResp>() {

                @Override
                public int compare(UserPrizeResp o1, UserPrizeResp o2) {
                    return o1.getRank() - o2.getRank();
                }
            });
        }
        activityInfoResp.setContentSections(contentSections);
        activityInfoResp.setPrizes(prizes);
        activityInfoResp.setStep(step);
        activityInfoResp.setStepDesc(stepDesc);
        activityInfoResp.setStepTime(stepTime);
        activityInfoResp.setStepTimeUnit(stepTimeUnit);
        activityInfoResp.setActivityTimeDesc(activityTimeDesc);
        activityInfoResp.setPrizeTimeDesc(prizeTimeDesc);
        activityInfoResp.setBottomStepDesc(bottomStepDesc);
        activityInfoResp.setUserPrizes(userPrizeResp);
    }

    private static String buildChineseTimeDesc(Date date) {
        int year = DateUtils.getYear(date);
        int month = DateUtils.getMonth(date);
        int day = DateUtils.getDay(date);
        return year + "年" + month + "月" + day + "日";
    }

    private static String buildStartEndTimeDesc(Date startDate, Date endDate) {
        return buildChineseTimeDesc(startDate) + "一" + buildChineseTimeDesc(endDate);
    }

    public static String buildSpaceInfo(SpaceInfo spaceInfo) {
        if (StringUtils.isNotBlank(spaceInfo.getInfo())) {
            return spaceInfo.getInfo();
        }
        if (spaceInfo.getType() == SpaceTypeEnum.BABY.getType()) {
        } else if (spaceInfo.getType() == SpaceTypeEnum.LOVE.getType()) {
        }
        return null;
    }

    public static void buildFeedCoverAndIllustration(SpaceFeedListResp spaceFeedListResp, FeedInfo feedInfo) {
        if (StringUtils.isEmpty(feedInfo.getIllustration())) {
            spaceFeedListResp.setCover(feedInfo.getCover());
        } else {
            String illustration = feedInfo.getIllustration();
            String[] illustrations = StringUtils.split(illustration, JSON_OBJECT_LIST_SPLIT_CHAR);
            List<String> illustrationList = new ArrayList<String>(illustrations.length);
            String cover = null;
            for (String illustrationInner : illustrations) {
                FeedInnerPhoto feedInnerPhoto;
                try {
                    feedInnerPhoto = GsonUtils.convert(illustrationInner, FeedInnerPhoto.class);
                } catch (Exception e) {
                    log.error("Convert " + illustrationInner + " to " + FeedInnerPhoto.class.getName()
                            + " failed.Errmsg:" + e.getMessage(), e);

                    throw ServiceException.getInternalException("Json数据格式错误");
                }
                illustrationList.add(feedInnerPhoto.getIndex(), feedInnerPhoto.getUrl());
                if (feedInnerPhoto.getIndex() == 0) {
                    cover = feedInnerPhoto.getUrl();
                }
            }
            spaceFeedListResp.setCover(cover);
            spaceFeedListResp.setIllustrations(illustrationList);
        }
    }

    public static void buildCoverAndIllustrations(ActivityWorksResp activityWorksResp, ActivityWorksInfo info) {
        if (StringUtils.isEmpty(info.getIllustration())) {
            activityWorksResp.setCover(info.getCover());
        } else {
            String illustration = info.getIllustration();
            String[] illustrations = StringUtils.split(illustration, ContentDescUtils.JSON_OBJECT_LIST_SPLIT_CHAR);
            List<String> illustrationList = new ArrayList<String>(illustrations.length);
            String cover = null;
            for (String illustrationInner : illustrations) {
                FeedInnerPhoto feedInnerPhoto;
                try {
                    feedInnerPhoto = GsonUtils.convert(illustrationInner, FeedInnerPhoto.class);
                } catch (Exception e) {
                    log.error("Convert " + illustrationInner + " to " + FeedInnerPhoto.class.getName()
                            + " failed.Errmsg:" + e.getMessage(), e);

                    throw ServiceException.getInternalException("Json数据格式错误");
                }
                illustrationList.add(feedInnerPhoto.getIndex(), feedInnerPhoto.getUrl());
                if (feedInnerPhoto.getIndex() == 0) {
                    cover = feedInnerPhoto.getUrl();
                }
            }
            activityWorksResp.setCover(cover);
            activityWorksResp.setIllustrations(illustrationList);
        }

    }

    public static String buildDateDesc(FeedInfo feedInfo) {
        Date ct = feedInfo.getCreateTime();
        return DateUtils.getDateMDStringValue(ct);
    }

    public static void buildFeedCoverAndIllustration(UserFeedListResp userFeedListResp, FeedInfo feedInfo) {
        if (StringUtils.isEmpty(feedInfo.getIllustration())) {
            userFeedListResp.setCover(feedInfo.getCover());
        } else {
            String illustration = feedInfo.getIllustration();
            String[] illustrations = StringUtils.split(illustration, JSON_OBJECT_LIST_SPLIT_CHAR);
            List<String> illustrationList = new ArrayList<String>(illustrations.length);
            String cover = null;
            for (String illustrationInner : illustrations) {
                FeedInnerPhoto feedInnerPhoto;
                try {
                    feedInnerPhoto = GsonUtils.convert(illustrationInner, FeedInnerPhoto.class);
                } catch (Exception e) {
                    log.error("Convert " + illustrationInner + " to " + FeedInnerPhoto.class.getName()
                            + " failed.Errmsg:" + e.getMessage(), e);

                    throw ServiceException.getInternalException("Json数据格式错误");
                }
                illustrationList.add(feedInnerPhoto.getIndex(), feedInnerPhoto.getUrl());
                if (feedInnerPhoto.getIndex() == 0) {
                    cover = feedInnerPhoto.getUrl();
                }
            }
            userFeedListResp.setCover(cover);
            userFeedListResp.setIllustrations(illustrationList);
        }
    }

    public static List<WikiPhaseResp> buildWikiContent(WikiInfo wikiInfo, boolean desc) {
        String content = wikiInfo.getContent();
        String[] contents = StringUtils.split(content, JSON_OBJECT_LIST_SPLIT_CHAR);
        List<WikiPhaseResp> ret = new ArrayList<WikiPhaseResp>(contents.length);
        for (String contentJson : contents) {
            WikiPhaseResp add;
            try {
                add = GsonUtils.convert(contentJson, WikiPhaseResp.class);
            } catch (Exception e) {
                String errMsg = "Convert " + contentJson + " to " + WikiPhaseResp.class.getName() + " failed.Errmsg:"
                        + e.getMessage();
                log.error(errMsg, e);
                continue;
            }
            ret.add(add);
        }
        return ret;
    }

    public static List<String> buildPhase(String activityRule) {
        if (StringUtils.isEmpty(activityRule)) {
            return new ArrayList<String>(0);
        }
        String[] rules = activityRule.split("<br/>");
        if (rules.length == 1) {
            rules = activityRule.split("\r\n");
        }
        if (rules.length == 1) {
            rules = activityRule.split("\n");
        }
        return Arrays.asList(rules);
    }
}
