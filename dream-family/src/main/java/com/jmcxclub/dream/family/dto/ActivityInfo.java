// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import java.util.Date;

import com.dreambox.core.DbField.ZERO_ENABLE;
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
    private String content;// 多段用<br/>或\r\n或\n来分隔，分隔符不能混合使用
    private Date startDate;
    private Date endDate;
    private String activityRule;
    @ZERO_ENABLE
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

    public String getActivityRule() {
        return activityRule;
    }

    public void setActivityRule(String activityRule) {
        this.activityRule = activityRule;
    }
}
