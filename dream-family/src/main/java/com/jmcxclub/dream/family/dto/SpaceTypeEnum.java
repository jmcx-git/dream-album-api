// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public enum SpaceTypeEnum {
    BABY(0, "亲子"), LOVE(1, "恋爱");
    private int type;
    private String desc;

    private SpaceTypeEnum(int type, String desc) {
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
