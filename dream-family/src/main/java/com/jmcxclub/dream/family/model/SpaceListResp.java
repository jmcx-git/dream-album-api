// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.jmcxclub.dream.family.dto.SpaceInfo;
import com.jmcxclub.dream.family.dto.SpaceStatInfo;


/**
 * 空间列表
 * 
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public class SpaceListResp {
    private int id;
    private String cover;// 封面图
    private boolean notice;// if user not the owner preview visit before the
                           // space update time will notice to true

    private int records;
    private int occupants;// 人气数
    private String title;// 空间名称
    private String desc;// 空间的描述

    public SpaceListResp(SpaceInfo spaceInfo, SpaceStatInfo curStat) {
        this.id = spaceInfo.getId();
        this.cover = spaceInfo.getCover();
        this.notice = false;
        this.title = spaceInfo.getTitle();
        this.desc = spaceInfo.getTitle();// TODO
        if (curStat != null) {
            this.records = curStat.getRecords();
            this.occupants = curStat.getOccupants();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isNotice() {
        return notice;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getOccupants() {
        return occupants;
    }

    public void setOccupants(int occupants) {
        this.occupants = occupants;
    }
}
