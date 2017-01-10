package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.StatusSerializable;

public class FeedStatInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = -9159754307368655660L;
    private int id;// reference feed id
    private int views;//may dose not use this time
    private int comments;
    private int likes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
