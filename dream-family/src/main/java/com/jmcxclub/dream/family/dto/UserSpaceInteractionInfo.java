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
public class UserSpaceInteractionInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1080323054319910939L;
    private int id;
    @UNIQUE_KEY
    private int userId;
    @UNIQUE_KEY
    private int spaceId;
    private int views;
    private int likes;
    private int comments;

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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }


}
