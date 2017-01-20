// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.io.Serializable;
import java.util.List;

import com.jmcxclub.dream.family.dto.WikiInfo;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public class WikiResp implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6784553349275329755L;
    private int id;
    private String title;
    private List<WikiPhaseResp> content;
    private String timeDesc;
    private long time;

    public WikiResp(WikiInfo wikiInfo, boolean desc) {
        this.id = wikiInfo.getId();
        this.title = wikiInfo.getTitle();
        this.content = ContentDescUtils.buildWikiContent(wikiInfo, desc);
        this.time = wikiInfo.getCreateTime().getTime();
        this.timeDesc = ContentDescUtils.buildNoticeTimeDesc(wikiInfo.getCreateTime());
    }

    public WikiResp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<WikiPhaseResp> getContent() {
        return content;
    }

    public void setContent(List<WikiPhaseResp> content) {
        this.content = content;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
