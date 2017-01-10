// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.StatusSerializable;

/**
 * 空间评论情况，注意支持emoji
 * 
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public class FeedCommentInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4481831914847839484L;
    private int id;
    private int userId;
    private int feedId;
    private String comment;
    // 如果此条评论是评论某条评论，那这个值对应的就是那条评论的id
    private Integer commentRefId;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCommentRefId() {
        return commentRefId;
    }

    public void setCommentRefId(Integer commentRefId) {
        this.commentRefId = commentRefId;
    }


}
