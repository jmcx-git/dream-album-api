// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.jmcxclub.dream.family.dto.SpaceInfo;
import com.jmcxclub.dream.family.dto.SpaceStatInfo;
import com.jmcxclub.dream.family.utils.ContentDescUtils;


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
    private String name;// 空间名称
    private String info;// 空间的描述
    private int type;// 空间类型 see SpaceTypeEnum

    private String timeDesc;// dd MMM.yy

    public SpaceListResp(SpaceInfo spaceInfo, SpaceStatInfo curStat) {
        this.id = spaceInfo.getId();
        this.cover = spaceInfo.getCover();
        this.notice = false;
        this.name = spaceInfo.getName();
        this.timeDesc = ContentDescUtils.buildEnglishTimeDesc(spaceInfo.getCreateTime());
        this.info = spaceInfo.getInfo();// TODO
        this.type = spaceInfo.getType();
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

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
