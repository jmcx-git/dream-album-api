// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class DiscoveryListResp {
    private int id;
    private String title;
    private String introduction;// 描述
    private int type;// see DiscoveryTypeEnum
    private String cover;
    private long participates;// 参与人数
    private int step;// see ActivityStepEnum
    private String startTimeDesc;
    private String endTimeDesc;// 最后结束时间
    private String stepDesc;

    public DiscoveryListResp(ActivityInfo activityInfo, long participates) {
        this.id = activityInfo.getId();
        this.title = activityInfo.getTitle();
        this.introduction = activityInfo.getIntroduction();
        this.type = DiscoveryTypeEnum.ACTIVITY.getType();
        this.cover = activityInfo.getCover();
        this.participates = participates;
        ContentDescUtils.buildActivityInfo(this, activityInfo);

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

    public long getParticipates() {
        return participates;
    }

    public void setParticipates(long participates) {
        this.participates = participates;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
