// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public enum ActivityFinishEnum {
    INIT(0, "未结束"), FINISH(1, "已结束");
    private int finish;
    private String desc;

    private ActivityFinishEnum(int finish, String desc) {
        this.finish = finish;
        this.desc = desc;
    }

    public int getFinish() {
        return finish;
    }

    public String getDesc() {
        return desc;
    }
}
