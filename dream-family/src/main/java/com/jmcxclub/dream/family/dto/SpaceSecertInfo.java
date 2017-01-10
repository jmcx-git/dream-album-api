// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.StatusSerializable;

/**
 * 用户空间分享码，只支持单向传播，如果用户分享其它用户分享给他的空间，则此用户无权查看
 * 
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public class SpaceSecertInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1867867524627291770L;
    private int id;
    private int spaceId;
    private String secert;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public String getSecert() {
        return secert;
    }

    public void setSecert(String secert) {
        this.secert = secert;
    }

}
