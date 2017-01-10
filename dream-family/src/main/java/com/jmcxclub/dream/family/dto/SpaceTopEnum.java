// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public enum SpaceTopEnum {
    NO(0, "正常"), YES(1, "置顶");

    private int top;
    private String desc;

    private SpaceTopEnum(int top, String desc) {
        this.top = top;
        this.desc = desc;
    }

    public int getTop() {
        return top;
    }

    public String getDesc() {
        return desc;
    }

}
