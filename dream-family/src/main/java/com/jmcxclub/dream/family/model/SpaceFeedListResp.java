// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.util.List;

import com.dreambox.core.dto.album.UserInfo;
import com.jmcxclub.dream.family.dto.FeedInfo;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public class SpaceFeedListResp {
    private int id;
    private String title;
    private String cover;
    // feed type
    private int type;
    private String content;
    private String resourceUrl;
    private long duration;// for video audio
    private String avatarUrl;
    private String nickname;
    private List<String> likeIcons;
    private List<FeedCommentInfoResp> comments;

    public SpaceFeedListResp(FeedInfo feedInfo, UserInfo authorUserInfo, List<String> likeIcons,
            List<FeedCommentInfoResp> comments) {
        this.id = feedInfo.getId();
        this.title = feedInfo.getTitle();
        this.cover = feedInfo.getCover();
        this.type = feedInfo.getType();
        this.content = feedInfo.getContent();
        this.resourceUrl = feedInfo.getResourceUrl();
        this.duration = feedInfo.getDuration();
        if (authorUserInfo != null) {
            this.avatarUrl = authorUserInfo.getAvatarUrl();
            this.nickname = authorUserInfo.getNickName();
        }
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

    public List<String> getLikeIcons() {
        return likeIcons;
    }

    public void setLikeIcons(List<String> likeIcons) {
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
}
