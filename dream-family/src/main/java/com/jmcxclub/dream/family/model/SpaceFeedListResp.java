// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.util.Date;
import java.util.List;

import com.dreambox.core.DbStatus;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.utils.DateUtils;
import com.jmcxclub.dream.family.dto.FeedInfo;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public class SpaceFeedListResp {
    private int id;
    private String title;
    private String cover;
    private String illustration;// 插图，当feed为广西,图片时 指的是feed中的图片(会进行图片合成，生成n宫格)
    // feed type
    private int type;
    private String content;
    private String resourceUrl;// for video audio
    private long duration;// for video audio
    private String authorOpenId;
    private String avatarUrl;
    private String nickname;
    private String timeDesc;
    private String dateDesc;
    private int ilike;
    private List<UserInfoResp> likeIcons;
    private List<FeedCommentInfoResp> comments;


    private static String buildDateDesc(FeedInfo feedInfo) {
        Date ct = feedInfo.getCreateTime();
        return DateUtils.getDateMDStringValue(ct);
    }
    

    public SpaceFeedListResp(FeedInfo feedInfo, UserInfo authorUserInfo, List<UserInfoResp> likeIcons,
            List<FeedCommentInfoResp> comments, boolean ilike) {
        this.id = feedInfo.getId();
        this.title = feedInfo.getTitle();
        this.cover = feedInfo.getCover();
        this.type = feedInfo.getType();
        this.content = feedInfo.getContent();
        this.resourceUrl = feedInfo.getResourceUrl();
        this.duration = feedInfo.getDuration();
        this.timeDesc =ContentDescUtils.buildTimeDesc(feedInfo);
        this.dateDesc = buildDateDesc(feedInfo);
        this.illustration = feedInfo.getIllustration();
        if (authorUserInfo != null) {
            this.avatarUrl = authorUserInfo.getAvatarUrl();
            this.nickname = authorUserInfo.getNickName();
            this.authorOpenId = authorUserInfo.getOpenId();
        }
        this.ilike = ilike ? DbStatus.STATUS_OK : DbStatus.STATUS_DEL;
        this.likeIcons = likeIcons;
        this.comments = comments;
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

    public List<UserInfoResp> getLikeIcons() {
        return likeIcons;
    }

    public void setLikeIcons(List<UserInfoResp> likeIcons) {
        this.likeIcons = likeIcons;
    }

    public List<FeedCommentInfoResp> getComments() {
        return comments;
    }

    public void setComments(List<FeedCommentInfoResp> comments) {
        this.comments = comments;
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

    public String getAuthorOpenId() {
        return authorOpenId;
    }

    public void setAuthorOpenId(String authorOpenId) {
        this.authorOpenId = authorOpenId;
    }

    public int getIlike() {
        return ilike;
    }

    public void setIlike(int ilike) {
        this.ilike = ilike;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }
}
