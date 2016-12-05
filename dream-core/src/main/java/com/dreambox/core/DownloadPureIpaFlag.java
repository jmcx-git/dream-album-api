// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core;

import com.dreambox.core.dto.EnumHtmlSelectType;

/**
 * @author luofei@appchina.com create date: 2016年3月31日
 *
 */
public enum DownloadPureIpaFlag {
    YES(0, "下载"), NO(1, "不下载");
    @EnumHtmlSelectType.KEY
    private int status;
    @EnumHtmlSelectType.VALUE
    private String desc;

    private DownloadPureIpaFlag(int status, String desc) {
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
