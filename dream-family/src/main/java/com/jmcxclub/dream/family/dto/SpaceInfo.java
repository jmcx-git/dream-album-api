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
    private String title;
    private String darlingName;
    private Date darlingBornDate;
    private int darlingType;
    private String icon;
    private String cover;
    private String info;

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

    public String getDarlingName() {
        return darlingName;
    }

    public void setDarlingName(String darlingName) {
        this.darlingName = darlingName;
    }

    public Date getDarlingBornDate() {
        return darlingBornDate;
    }

    public void setDarlingBornDate(Date darlingBornDate) {
        this.darlingBornDate = darlingBornDate;
    }

    public int getDarlingType() {
        return darlingType;
    }

    public void setDarlingType(int darlingType) {
        this.darlingType = darlingType;
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
}
