// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.DbKey.UNIQUE_KEY;
import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public class FeedLikeInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3758885060520774509L;
    private int id;
    @UNIQUE_KEY
    private int userId;
    @UNIQUE_KEY
    private int feedId;

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

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }
}
