// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: Jan 20, 2017
 *
 */
public class WikiInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7752705828119337619L;
    private int id;
    private String title;
    private String content;// List JSON desc, img see WikiPhaseResp

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
