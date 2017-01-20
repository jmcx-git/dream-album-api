// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.io.Serializable;


/**
 * @author mokous86@gmail.com create date: Jan 14, 2017
 *
 */
public class UserPrizeResp implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 7345036100339309403L;
    private int rank;
    private int vote;
    private String name;
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getVote() {
        return vote;
    }
    public void setVote(int vote) {
        this.vote = vote;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
