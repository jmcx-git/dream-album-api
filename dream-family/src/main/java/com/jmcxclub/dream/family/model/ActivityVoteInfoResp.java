// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class ActivityVoteInfoResp {
    private int id;
    private String title;// 投票宣言
    private int rank;
    private int votes;
    private int self;// 为1则代表是自己参与活动信息

    private int feedId;
    private String feedTitle;
    private String feedCover;
    // feed type
    private int feedType;
    private String feedContent;
    private String feedResourceUrl;
    private long feedDuration;// for video audio
    private String feedAvatarUrl;
    private String feedNickname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedCover() {
        return feedCover;
    }

    public void setFeedCover(String feedCover) {
        this.feedCover = feedCover;
    }

    public int getFeedType() {
        return feedType;
    }

    public void setFeedType(int feedType) {
        this.feedType = feedType;
    }

    public String getFeedContent() {
        return feedContent;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public String getFeedResourceUrl() {
        return feedResourceUrl;
    }

    public void setFeedResourceUrl(String feedResourceUrl) {
        this.feedResourceUrl = feedResourceUrl;
    }

    public long getFeedDuration() {
        return feedDuration;
    }

    public void setFeedDuration(long feedDuration) {
        this.feedDuration = feedDuration;
    }

    public String getFeedAvatarUrl() {
        return feedAvatarUrl;
    }

    public void setFeedAvatarUrl(String feedAvatarUrl) {
        this.feedAvatarUrl = feedAvatarUrl;
    }

    public String getFeedNickname() {
        return feedNickname;
    }

    public void setFeedNickname(String feedNickname) {
        this.feedNickname = feedNickname;
    }

    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
        this.self = self;
    }
}
