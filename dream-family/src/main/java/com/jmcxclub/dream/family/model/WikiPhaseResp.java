// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.io.Serializable;

/**
 * @author mokous86@gmail.com create date: Jan 20, 2017
 *
 */
public class WikiPhaseResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6404870646672940588L;
    private String content;
    private String imgUrl;

    public WikiPhaseResp(String content, String imgUrl) {
        super();
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
