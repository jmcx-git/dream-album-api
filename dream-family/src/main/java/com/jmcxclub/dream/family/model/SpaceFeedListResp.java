// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.util.List;

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
    private Integer duration;// for video audio
    private String avatar;
    private String nickname;
    private List<String> likeIcons;
    private List<FeedCommentInfoResp> comments;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
