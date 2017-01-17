// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public enum NoticeBuildEnum {
    INIT(0, "待构建"), BUILDED(1, "已构建");
    private int flag;
    private String desc;

    private NoticeBuildEnum(int flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public int getFlag() {
        return flag;
    }

    public String getDesc() {
        return desc;
    }

}
