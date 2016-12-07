// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

import com.dreambox.core.DbStatus;

/**
 * @author git@jmcxclub.com create date: Dec 6, 2016
 *
 */
public class UserInfo extends DbStatus {
    /**
     * 
     */
    private static final long serialVersionUID = -3105486978234970308L;
    private int id;
    private String nickName;// refer from wx info.nickName
    private int gender;// refer from wx info.gender[性别 0:未知、1:男、2:女]
    private String city;
    private String province;
    private String country;
    private String avatarUrl;// refer from wx info.avatorUrl

    private String openId;// refer from wx info.openId[用户的标识，只对当前小程序唯一]
    private String unionId;// refer from wx info.unionId;[多平台唯一标识]
    private String appid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }


}
