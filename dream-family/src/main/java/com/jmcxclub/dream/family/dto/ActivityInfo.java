// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import java.util.Date;

import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class ActivityInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = 22017033387361401L;
    private int id;
    private String cover;
    private String title;
    private String introduction;
    private String content;
    private String contentCss;
    private Date startDate;
    private Date endDate;
    private int finish;// 只有中奖名单出来后，才算结束 0 未结束 1结束

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentCss() {
        return contentCss;
    }

    public void setContentCss(String contentCss) {
        this.contentCss = contentCss;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }
}
