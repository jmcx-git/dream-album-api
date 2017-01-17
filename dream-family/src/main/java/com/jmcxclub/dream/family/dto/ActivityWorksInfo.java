// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.DbKey.UNIQUE_KEY;
import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class ActivityWorksInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7238929182382417883L;
    private int id;
    @UNIQUE_KEY
    private int userId;// 参赛用户
    @UNIQUE_KEY
    private int activityId;// 活动id
    private int type;// 作品类型 0：非视频 1:视频 2:音频
    private String cover;// 如果是以type==0参赛,则此处和resourceUrl一样,否则此值为对应的资源封面
    private String resourceUrl;// 如果是以type==0参赛,则此处和cover一样,否则此值为对应的资源地址
    private Long durations;// 参赛作品如果是视频,音频则为其时长
    private String solgan;// 参赛作品口号
    private String desc;// 参赛作品描述

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getSolgan() {
        return solgan;
    }

    public void setSolgan(String solgan) {
        this.solgan = solgan;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Long getDurations() {
        return durations;
    }

    public void setDurations(Long durations) {
        this.durations = durations;
    }
}
