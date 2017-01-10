// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.StatusSerializable;

/**
 * 用户点赞空间 status控制赞是否有效
 * 
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public class SpaceLikeInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7442295169989967872L;
    private int id;
    private int spaceId;
    private int userId;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
