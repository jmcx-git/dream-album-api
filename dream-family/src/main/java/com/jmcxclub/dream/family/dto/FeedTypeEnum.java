// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public enum FeedTypeEnum {
    DIARY(1, "日记"), AUDIO(2, "录音"), PHOTO(0, "照片"), VIDEO(3, "视频");
    private int type;
    private String desc;

    private FeedTypeEnum(int type, String desc) {
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
