// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.utils;

import com.dreambox.core.utils.DateUtils;
import com.jmcxclub.dream.family.dto.ActivityFinishEnum;
import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.dto.FeedInfo;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;
import com.jmcxclub.dream.family.model.DiscoveryListResp;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class ContentDescUtils {
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
        String startTimeDesc = "";
        String endTimeDesc = "";// 最后结束时间
        String stepDesc = "";
        String format = DateUtils.formatStr(activityInfo.getStartDate(), "");
        long curTimeMillis = System.currentTimeMillis();
        if (activityInfo.getFinish() == ActivityFinishEnum.FINISH.getFinish()) {
            
        }
        if (activityInfo.getStartDate().getTime() > curTimeMillis) {

        } else if (activityInfo.getEndDate().getTime() > curTimeMillis) {
        } else {
        }
    }
}
