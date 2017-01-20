// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import java.io.Serializable;

/**
 * @author mokous86@gmail.com create date: Jan 20, 2017
 *
 */
public class FeedInnerPhoto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1323681553451232686L;

    public FeedInnerPhoto(int index, String url) {
        super();
        this.index = index;
        this.url = url;
    }

    private int index;
    private String url;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
