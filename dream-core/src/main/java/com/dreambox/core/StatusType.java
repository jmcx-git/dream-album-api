// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core;

import com.dreambox.core.dto.EnumHtmlSelectType;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public enum StatusType {
    STATUS_ALL(9999, "全部"), STATUS_OK(0, "正常"), STATUS_DEL(-1, "删除");
    @EnumHtmlSelectType.KEY
    private int status;
    @EnumHtmlSelectType.VALUE
    private String desc;

    private StatusType(int status, String desc) {
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
