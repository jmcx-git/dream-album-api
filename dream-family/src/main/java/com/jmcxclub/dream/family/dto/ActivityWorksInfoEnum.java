// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public enum ActivityWorksInfoEnum {
    NORMAL(0, "图文"), AUDIO(2, "音频"), VIDEO(3, "视频");
    private int type;
    private String desc;

    private ActivityWorksInfoEnum(int type, String desc) {
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
