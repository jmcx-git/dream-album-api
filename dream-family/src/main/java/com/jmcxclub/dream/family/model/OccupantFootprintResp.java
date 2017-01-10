// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.dreambox.core.dto.album.UserInfo;
import com.jmcxclub.dream.family.dto.UserSpaceRelationshipInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public class OccupantFootprintResp {
    private int id;
    private String nickname;
    private String avatarUrl;
    private String info;// BUILD BY SYSTEM

    public OccupantFootprintResp(UserSpaceRelationshipInfo info, UserInfo userInfo) {
        this.id = info.getUserId();
        this.info = buildInfo(info);
        if (userInfo != null) {
            this.nickname = userInfo.getNickName();
            this.avatarUrl = userInfo.getAvatarUrl();
        }
    }

    private static String buildInfo(UserSpaceRelationshipInfo info) {
        long curTimeMillis = System.currentTimeMillis();
        long preVisiMillis = info.getUpdateTime().getTime();

        long minutes = (curTimeMillis - preVisiMillis) / 6000;
        String minDesc = "";
        if (minutes < 1) {
            minDesc = "正在查看";
        } else {
            long hours = minutes / 60;
            if (hours <= 0) {
                minDesc = minutes + "分钟前来过";
            } else {
                long days = hours / 24;
                if (days <= 0) {
                    minDesc = hours + "小时前来过";
                } else {
                    if (days <= 355) {
                        minDesc = days + "天前来过";
                    } else {
                        minDesc = "1年前来过";
                    }
                }
            }
        }
        return minDesc + "," + "共来过" + info.getViews() + "次";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
