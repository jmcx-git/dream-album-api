// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import java.util.Date;

import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public class SpaceInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8399503380112124874L;
    private int id;
    // ownerId
    private int userId;
    private String name;
    private Date bornDate;
    private Integer gender;
    private int type;
    private String icon;
    private String cover;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDarlingType() {
        return type;
    }

    public void setDarlingType(int darlingType) {
        this.type = darlingType;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
