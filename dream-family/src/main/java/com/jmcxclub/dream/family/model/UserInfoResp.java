// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.io.Serializable;

import com.dreambox.core.dto.album.UserInfo;

/**
 * @author mokous86@gmail.com create date: Dec 30, 2016
 *
 */
public class UserInfoResp implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -5540656235380143846L;
    private String openId;
    private String nickName;
    private String avatarUrl;
    private int gender;// refer from wx info.gender[性别 0:未知、1:男、2:女]

    public UserInfoResp(UserInfo g) {
        this.openId = g.getOpenId();
        this.nickName = g.getNickName();
        this.avatarUrl = g.getAvatarUrl();
        this.gender = g.getGender();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
