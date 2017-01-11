// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.dreambox.core.dto.album.UserInfo;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public class OccupantFootprintResp {
    private String nickname;
    private String openId;
    private String avatarUrl;
    private String info;// BUILD BY SYSTEM

    public OccupantFootprintResp(UserSpaceInteractionInfo info, UserInfo userInfo) {
        this.info = ContentDescUtils.buildUserVisitSpaceInfo(info);
        if (userInfo != null) {
            this.nickname = userInfo.getNickName();
            this.avatarUrl = userInfo.getAvatarUrl();
            this.openId = userInfo.getOpenId();
        }
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
