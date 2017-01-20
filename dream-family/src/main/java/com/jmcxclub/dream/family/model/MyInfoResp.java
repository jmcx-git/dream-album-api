// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.util.List;

import com.dreambox.core.dto.album.UserInfo;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public class MyInfoResp {
    private String nickname;
    private String avatarUrl;
    private int notices;
    private List<String> bottomDesc;

    public MyInfoResp(UserInfo userInfo, boolean notice) {
        this.nickname = userInfo.getNickName();
        this.avatarUrl = userInfo.getAvatarUrl();
        this.notices = notice ? 1 : 0;
        this.bottomDesc = new AboutUsResp().getBottomDesctions();
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

    public List<String> getBottomDesc() {
        return bottomDesc;
    }

    public void setBottomDesc(List<String> bottomDesc) {
        this.bottomDesc = bottomDesc;
    }
}
