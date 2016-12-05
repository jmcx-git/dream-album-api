// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.dreambox.core;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class DbStatus {
    public static final int STATUS_ALL = StatusType.STATUS_ALL.getStatus();
    public static final int STATUS_OK = StatusType.STATUS_OK.getStatus();
    public static final int STATUS_DEL = StatusType.STATUS_DEL.getStatus();
    public static final int ID_ALL = -1;
    public static final String STATUS_COLUMN = "status";
    public static final String ID_COLUMN = "id";
    public static final String ALL_DESC = "all";
    public static final int ALL_ID_DESC = 9999;
    public static final Map<String, String> STATUS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 8542698439523192232L;

        {
            for (StatusType type : StatusType.values()) {
                put(String.valueOf(type.getStatus()), type.getDesc());
            }
        }
    };
    private int status;
    private Date updateTime;
    private Date createTime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DbStatus [status=" + status + ", updateTime=" + updateTime + ", createtTime=" + createTime + "]";
    }

    public boolean illegalStatus() {
        return status != STATUS_DEL && status != STATUS_OK;
    }

    public boolean isDel() {
        return !isOk();
    }

    public boolean isOk() {
        return status == STATUS_OK;
    }
}
