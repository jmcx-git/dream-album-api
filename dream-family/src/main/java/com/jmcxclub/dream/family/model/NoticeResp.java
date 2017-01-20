// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.jmcxclub.dream.family.dto.SystemNoticeInfo;
import com.jmcxclub.dream.family.dto.UserNoticeInfo;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public class NoticeResp {
    private int id;
    private int type;// 是否官方信息0官方 1个人
    private String avatarUrl;// 消息为个人时可用，否则使用默认图标【志乐会提供】
    private String resourceUrl;
    private String nickname;// 消息为个人时可用【否则使用程序名称】
    private String content;
    private String timeDesc;
    private long time;

    public NoticeResp(UserNoticeInfo userNoticeInfo) {
        this.id = userNoticeInfo.getId();
        this.type = 0;
        this.content = userNoticeInfo.getDesc();
        this.time = userNoticeInfo.getCreateTime().getTime();
        this.timeDesc = ContentDescUtils.buildNoticeTimeDesc(userNoticeInfo.getCreateTime());
        this.resourceUrl = userNoticeInfo.getImgUrl();
    }

    public NoticeResp(SystemNoticeInfo systemNoticeInfo) {
        this.id = systemNoticeInfo.getId();
        this.type = 0;
        this.content = systemNoticeInfo.getDesc();
        this.time = systemNoticeInfo.getCreateTime().getTime();
        this.timeDesc = ContentDescUtils.buildNoticeTimeDesc(systemNoticeInfo.getCreateTime());
    }

    public NoticeResp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
