// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public enum UserNoticeReadEnum {
    INIT(0, "未读"), READ(1, "已读");
    private int flag;
    private String desc;

    private UserNoticeReadEnum(int flag, String desc) {
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
