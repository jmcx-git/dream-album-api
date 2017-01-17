// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
public class SystemNoticeInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8604531825229465197L;
    private int id;
    private String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
