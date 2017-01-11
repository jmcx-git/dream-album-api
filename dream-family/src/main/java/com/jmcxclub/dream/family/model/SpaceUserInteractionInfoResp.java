// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.dreambox.core.dto.album.UserInfo;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class SpaceUserInteractionInfoResp {
    private String openId;
    private String avatalUrl;
    private String nickname;
    private String info;
    private int likes;
    private int comments;

    public SpaceUserInteractionInfoResp(UserInfo userInfo, UserSpaceInteractionInfo userSpaceInteractionInfo) {
        this.openId = userInfo.getOpenId();
        this.avatalUrl = userInfo.getAvatarUrl();
        this.nickname = userInfo.getNickName();
        this.likes = userSpaceInteractionInfo.getLikes();
        this.comments = userSpaceInteractionInfo.getComments();
        this.info = ContentDescUtils.buildUserVisitSpaceInfo(userSpaceInteractionInfo);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAvatalUrl() {
        return avatalUrl;
    }

    public void setAvatalUrl(String avatalUrl) {
        this.avatalUrl = avatalUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
