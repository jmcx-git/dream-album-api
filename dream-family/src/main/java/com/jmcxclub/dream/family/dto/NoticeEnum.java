// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
public enum NoticeEnum {
    USER(1, "用户消息"), SYSTEM(0, "系统消息");
    private int type;
    private String desc;

    private NoticeEnum(int type, String desc) {
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
