// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.dreambox.core.utils.DateUtils;
import com.jmcxclub.dream.family.dto.ActivityFinishEnum;
import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.dto.ActivityWorksInfo;
import com.jmcxclub.dream.family.dto.FeedInfo;
import com.jmcxclub.dream.family.dto.PrizeInfo;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;
import com.jmcxclub.dream.family.model.DiscoveryListResp;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class ContentDescUtils {
    public static String decode(String content) {
        if (StringUtils.isNotEmpty(content)) {
            try {
                content = URLDecoder.decode(content, "utf8");
            } catch (UnsupportedEncodingException e) {
            }
        }
        return content;
    }

    public static String buildUserVisitSpaceInfo(UserSpaceInteractionInfo info) {
        if (info == null) {
            return "未发现其踪迹";
        }
        long curTimeMillis = System.currentTimeMillis();
        long preVisiMillis = info.getUpdateTime().getTime();

        long minutes = (curTimeMillis - preVisiMillis) / 6000;
        String minDesc = "";
        if (minutes < 1) {
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

    public static void buildActivityInfo(DiscoveryListResp discoveryListResp, ActivityInfo activityInfo) {
        String stepDesc = "";
        String startTimeDesc = DateUtils.formatStr(activityInfo.getStartDate(), "dd MMM.yyyy");
        if (activityInfo.getFinish() == ActivityFinishEnum.FINISH.getFinish()) {
            stepDesc = "已结束，点击查看结果";
        } else {
            long curTimeMillis = System.currentTimeMillis();
            long minutes = (curTimeMillis - activityInfo.getStartDate().getTime()) / 60000;
            if (minutes < 0) {
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
                long leftMinutes = (activityInfo.getEndDate().getTime() - curTimeMillis) / 60000;
                if (leftMinutes > 0) {
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
                } else {
                    stepDesc = "投票结束，正在出奖，祝您好运";
                }
            }
        }
        discoveryListResp.setStepDesc(stepDesc);
        discoveryListResp.setStartTimeDesc(startTimeDesc);
    }

    public static String buildNotice(PrizeInfo prizeInfo, ActivityInfo activityInfo, ActivityWorksInfo activityWorksInfo) {
        // 恭喜您，在***活动中，获得大奖一份，请前往发现，找到活动，获取相关信息，或者通过下方与客房对话图标联系我们。
        return "恭喜您，在【" + activityInfo.getTitle() + "】活动中，获得大奖一份，请前往发现,找到活动，获取相关信息，或者点击下方与客服对话图标联系我们。";
    }

    public static String buildNoticeTimeDesc(Date createTime) {
        long minuteDiff = (System.currentTimeMillis() - createTime.getTime()) / 60000;
        if (minuteDiff <= 0) {
            return "刚刚发布";
        }
        long hour = minuteDiff / 60;
        if (hour == 0) {
            return minuteDiff + "分钟前";
        }
        long day = hour / 24;
        if (day == 0) {
            return hour + "小时前";
        }
        if (day == 1) {
            return "昨天发布";
        }
        if (day == 2) {
            return "前天发布";
        }
        long year = day / 365;
        if (year == 0) {
            return day + "天前发布";
        }
        if (year == 1) {
            return "去年前发布";
        }
        if (year == 2) {
            return "前年前发布";
        }
        return year + "年前发布";
    }
}
