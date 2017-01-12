// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.jmcxclub.dream.family.dto.ActivityInfo;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class DiscoveryListResp {
    private int id;
    private String title;
    private String intr;// 描述
    private int type;// see DiscoveryTypeEnum
    private String cover;
    private long endTimeMillis;// 最后结束时间
    private int participates;// 参与人数
    private int step;// see ActivityStepEnum
    private String startTimeDesc;
    private String stepDesc;

    public DiscoveryListResp(ActivityInfo activityInfo) {
        
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getEndTimeMillis() {
        return endTimeMillis;
    }

    public void setEndTimeMillis(long endTimeMillis) {
        this.endTimeMillis = endTimeMillis;
    }

    public int getParticipates() {
        return participates;
    }

    public void setParticipates(int participates) {
        this.participates = participates;
    }

    public String getIntr() {
        return intr;
    }

    public void setIntr(String intr) {
        this.intr = intr;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getStartTimeDesc() {
        return startTimeDesc;
    }

    public void setStartTimeDesc(String startTimeDesc) {
        this.startTimeDesc = startTimeDesc;
    }

    public String getStepDesc() {
        return stepDesc;
    }

    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }
}
