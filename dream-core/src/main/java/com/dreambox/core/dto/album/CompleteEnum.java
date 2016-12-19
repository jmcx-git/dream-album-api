// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

/**
 * @author mokous86@gmail.com create date: Dec 18, 2016
 *
 */
public enum CompleteEnum {
    INIT(0, "待完成"), COMPLETED(1, "已完成");
    private int status;
    private String desc;

    private CompleteEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}
