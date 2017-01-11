// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public enum DiscoveryTypeEnum {
    ACTIVITY(0, "活动");

    private int type;
    private String desc;

    private DiscoveryTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
