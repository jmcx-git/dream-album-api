// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.util.List;

/**
 * @author mokous86@gmail.com create date: Jan 14, 2017
 *
 */
public class UserPrizeResp {
    private String desc;
    private int rank;
    private List<UserInfoResp> userInfos;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<UserInfoResp> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfoResp> userInfos) {
        this.userInfos = userInfos;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
