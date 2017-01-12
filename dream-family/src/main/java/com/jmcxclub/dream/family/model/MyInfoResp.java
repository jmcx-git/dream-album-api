// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.dreambox.core.dto.album.UserInfo;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public class MyInfoResp {
    private String nickname;
    private String avatarUrl;
    private int notices;

    public MyInfoResp(UserInfo userInfo, boolean notice) {
        this.nickname = userInfo.getNickName();
        this.avatarUrl = userInfo.getAvatarUrl();
        this.notices = notice ? 1 : 0;
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

    public int getNotices() {
        return notices;
    }

    public void setNotices(int notices) {
        this.notices = notices;
    }
}
