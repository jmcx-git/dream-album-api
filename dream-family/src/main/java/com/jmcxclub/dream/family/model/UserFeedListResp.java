// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.util.Date;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.utils.DateUtils;
import com.jmcxclub.dream.family.dto.FeedInfo;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class UserFeedListResp {
    private int id;
    private String title;
    private String cover;
    // feed type
    private int type;
    private String content;
    private String resourceUrl;
    private long duration;// for video audio
    private String avatarUrl;
    private String openId;
    private String nickname;
    private String timeDesc;
    private String dateDesc;


    private static String buildDateDesc(FeedInfo feedInfo) {
        Date ct = feedInfo.getCreateTime();
        return DateUtils.getDateMDStringValue(ct);
    }

    private static String buildTimeDesc(FeedInfo feedInfo) {
        long curTimeMillis = System.currentTimeMillis();
        long preVisiMillis = feedInfo.getCreateTime().getTime();
        long minutes = (curTimeMillis - preVisiMillis) / 6000;
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

    public UserFeedListResp(FeedInfo feedInfo, UserInfo authorUserInfo) {
        this.id = feedInfo.getId();
        this.title = feedInfo.getTitle();
        this.cover = feedInfo.getCover();
        this.type = feedInfo.getType();
        this.content = feedInfo.getContent();
        this.resourceUrl = feedInfo.getResourceUrl();
        this.duration = feedInfo.getDuration();
        this.timeDesc = buildTimeDesc(feedInfo);
        this.dateDesc = buildDateDesc(feedInfo);
        if (authorUserInfo != null) {
            this.avatarUrl = authorUserInfo.getAvatarUrl();
            this.nickname = authorUserInfo.getNickName();
            this.openId = authorUserInfo.getOpenId();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public String getDateDesc() {
        return dateDesc;
    }

    public void setDateDesc(String dateDesc) {
        this.dateDesc = dateDesc;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
