// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.util.Date;

import com.dreambox.core.dto.album.UserInfo;
import com.jmcxclub.dream.family.dto.SpaceInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public class SpaceInfoResp {
    private int id;
    private String name;// 空间名称
    private String info;// 空间的描述
    private String icon;// 空间icon
    private String cover;// 空间背景图
    private Date bornDate;
    private Integer gender;
    private int type;

    public SpaceInfoResp(SpaceInfo spaceInfo, UserInfo owner) {
        this.id = spaceInfo.getId();
        this.name = spaceInfo.getName();
        this.icon = spaceInfo.getIcon();
        this.cover = spaceInfo.getCover();
        this.bornDate = spaceInfo.getBornDate();
        this.gender = spaceInfo.getGender();
        this.type = spaceInfo.getType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
