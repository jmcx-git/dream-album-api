// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core;

import com.dreambox.core.dto.EnumHtmlSelectType;

/**
 * @author luofei@appchina.com create date: 2016年3月31日
 *
 */
public enum TaskStatus {
    ALL(StatusType.STATUS_ALL.getStatus(), StatusType.STATUS_ALL.getDesc()), INIT(0, "任务等待执行"), PROCESSING(1, "任务执行中"),
    DONE(2, "任务完成"), FAILED(-1, "任务失败");
    private TaskStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    @EnumHtmlSelectType.KEY
    private int status;
    @EnumHtmlSelectType.VALUE
    private String desc;

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }


}
