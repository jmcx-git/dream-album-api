// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import java.util.Date;

import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
public class UserReadNoticeRecord extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3299870136143256792L;
    private int id;
    private Date readTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

}
