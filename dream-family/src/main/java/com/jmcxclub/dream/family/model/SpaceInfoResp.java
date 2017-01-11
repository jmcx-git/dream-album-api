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
    private String name;// 空间名称
    private String info;// 空间的描述
    private String avatarUrl;
    private List<UserInfoResp> occupantInfos;

    public SpaceInfoResp(SpaceInfo spaceInfo, SpaceStatInfo stat, List<UserInfo> userInfos) {
        this.id = spaceInfo.getId();
        if (stat != null) {
            this.records = stat.getRecords();
            this.occupants = stat.getOccupants();
        }
        this.name = spaceInfo.getName();
        this.avatarUrl = spaceInfo.getIcon();
        if (CollectionUtils.notEmptyAndNull(userInfos)) {
            List<UserInfoResp> occupantInfos = new ArrayList<UserInfoResp>(userInfos.size());
            for (UserInfo userInfo : userInfos) {
                occupantInfos.add(new UserInfoResp(userInfo));
            }
            this.occupantInfos = occupantInfos;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public List<UserInfoResp> getOccupantInfos() {
        return occupantInfos;
    }

    public void setOccupantInfos(List<UserInfoResp> occupantInfos) {
        this.occupantInfos = occupantInfos;
    }
}
