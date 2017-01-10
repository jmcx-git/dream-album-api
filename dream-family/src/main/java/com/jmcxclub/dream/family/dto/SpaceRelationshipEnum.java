// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public enum SpaceRelationshipEnum {
    OWNER(1, "所有者"), OCCUPANTS(0, "受邀者");
    private int flag;
    private String desc;

    private SpaceRelationshipEnum(int flag, String desc) {
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
