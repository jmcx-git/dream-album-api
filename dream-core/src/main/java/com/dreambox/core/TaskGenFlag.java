// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core;

/**
 * @author luofei@appchina.com create date: 2016年3月31日
 *
 */
public enum TaskGenFlag {
    ALL(StatusType.STATUS_ALL.getStatus(), StatusType.STATUS_ALL.getDesc()), INIT(0, "任务等待生成"), DONE(1, "任务生成完毕");
    private TaskGenFlag(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private int status;
    private String desc;

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }


}
