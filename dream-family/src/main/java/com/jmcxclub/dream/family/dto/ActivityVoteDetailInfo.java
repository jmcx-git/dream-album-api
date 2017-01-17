// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import java.util.Date;

import com.dreambox.core.dto.DbKey.UNIQUE_KEY;
import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class ActivityVoteDetailInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4047580627451170277L;
    private int id;
    @UNIQUE_KEY
    private int userId;
    @UNIQUE_KEY
    private int worksId;// reference ActivityWorksInfo
    @UNIQUE_KEY
    private int voteDate;
    private Date voteTime;
    private String ip;

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

    public int getWorksId() {
        return worksId;
    }

    public void setWorksId(int worksId) {
        this.worksId = worksId;
    }

    public int getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(int voteDate) {
        this.voteDate = voteDate;
    }

    public Date getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Date voteTime) {
        this.voteTime = voteTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
