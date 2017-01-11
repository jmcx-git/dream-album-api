// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.util.ArrayList;
import java.util.List;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.web.utils.CollectionUtils;
import com.jmcxclub.dream.family.dto.SpaceInfo;
import com.jmcxclub.dream.family.dto.SpaceStatInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public class SpaceInfoResp {
    private int id;
    private int records;
    private int occupants;// 人气数
    private String title;// 空间名称
    private String desc;// 空间的描述
    private String avatarUrl;
    private List<String> occupantAvatarUrls;

    public SpaceInfoResp(SpaceInfo spaceInfo, SpaceStatInfo stat, List<UserInfo> userInfos) {
        this.id = spaceInfo.getId();
        this.records = stat.getRecords();
        this.occupants = stat.getOccupants();
        this.title = spaceInfo.getTitle();
        this.avatarUrl = spaceInfo.getIcon();
        if (CollectionUtils.notEmptyAndNull(userInfos)) {
            List<String> urls = new ArrayList<String>(userInfos.size());
            for (UserInfo userInfo : userInfos) {
                urls.add(userInfo.getAvatarUrl());
            }
            this.occupantAvatarUrls = urls;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<String> getOccupantAvatarUrls() {
        return occupantAvatarUrls;
    }

    public void setOccupantAvatarUrls(List<String> occupantAvatarUrls) {
        this.occupantAvatarUrls = occupantAvatarUrls;
    }
}