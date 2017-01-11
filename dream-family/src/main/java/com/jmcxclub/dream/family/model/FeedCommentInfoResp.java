// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.dreambox.core.dto.album.UserInfo;
import com.jmcxclub.dream.family.dto.FeedCommentInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public class FeedCommentInfoResp {
    private int id;
    private String nickname;
    private String avatarUrl;
    private String comment;

    public FeedCommentInfoResp(FeedCommentInfo feedCommentInfo, UserInfo commentUserInfo) {
        this.id = feedCommentInfo.getId();
        this.comment = feedCommentInfo.getComment();
        if (commentUserInfo != null) {
            this.nickname = commentUserInfo.getNickName();
            this.avatarUrl = commentUserInfo.getAvatarUrl();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
