// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.DbKey.UNIQUE_KEY;
import com.dreambox.core.dto.StatusSerializable;

/**
 * 用户加入了哪些空间 except their self create space
 * 
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public class UserSpaceRelationshipInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1080323054319910939L;
    private int id;
    @UNIQUE_KEY
    private int userId;
    @UNIQUE_KEY
    private int spaceId;
    // 邀请过来的 或者用户自己创建的 排序第一因子
    private int relationship;
    private int top;// 置顶 排序第二因子

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

}
