// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.util.List;

import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.dto.ActivityPrizeInfo;
import com.jmcxclub.dream.family.dto.ActivityWorksExampleInfo;
import com.jmcxclub.dream.family.dto.PrizeInfo;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public class ActivityInfoResp {
    private int id;
    private String title;
    private String introduction;// 描述
    private String cover;
    private int participates;// 参与人数
    private String activityRule;// 活动规则
    private List<ActivityWorksExampleInfo> examples;
    private int joined;// 当前用户是否已参赛0 not joined 1 joined

    // need build
    private long stepTime;// 距结束N天中的天在step==1时使用
    private String stepTimeUnit;// 距结束N天中的N;在step==1时使用
    private int step;// see ActivityStepEnum for css style
    private String stepDesc;// 在step!=1时使用此数据
    private List<String> contentSections;// 活动段落
    private String activityTimeDesc;// 活动时间
    private List<ActivityPrizeResp> prizes;

    public ActivityInfoResp(ActivityInfo info, List<ActivityWorksExampleInfo> examples,
            List<ActivityPrizeInfo> activityPrizeInfos, List<PrizeInfo> prizeInfos, boolean joined) {
        this.id = info.getId();
        this.title = info.getTitle();
        this.introduction = info.getIntroduction();
        this.cover = info.getCover();
        this.joined = joined ? 1 : 0;
        this.examples = examples;
        this.activityRule = info.getActivityRule();
        ContentDescUtils.buildActivityInfoRespOthers(this, info, activityPrizeInfos, prizeInfos);

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStepDesc() {
        return stepDesc;
    }

    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getParticipates() {
        return participates;
    }

    public void setParticipates(int participates) {
        this.participates = participates;
    }

    public List<String> getContentSections() {
        return contentSections;
    }

    public void setContentSections(List<String> contentSections) {
        this.contentSections = contentSections;
    }

    public String getActivityRule() {
        return activityRule;
    }

    public void setActivityRule(String activityRule) {
        this.activityRule = activityRule;
    }

    public String getActivityTimeDesc() {
        return activityTimeDesc;
    }

    public void setActivityTimeDesc(String activityTimeDesc) {
        this.activityTimeDesc = activityTimeDesc;
    }

    public List<ActivityWorksExampleInfo> getExamples() {
        return examples;
    }

    public void setExamples(List<ActivityWorksExampleInfo> examples) {
        this.examples = examples;
    }

    public List<ActivityPrizeResp> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<ActivityPrizeResp> prizes) {
        this.prizes = prizes;
    }

    public int getJoined() {
        return joined;
    }

    public void setJoined(int joined) {
        this.joined = joined;
    }

    public static class ActivityPrizeResp {
        private String rankDesc;
        private String title;
        private String img;

        public ActivityPrizeResp(String rankDesc, String title, String img) {
            super();
            this.rankDesc = rankDesc;
            this.title = title;
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getRankDesc() {
            return rankDesc;
        }

        public void setRankDesc(String rankDesc) {
            this.rankDesc = rankDesc;
        }
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public long getStepTime() {
        return stepTime;
    }

    public void setStepTime(long stepTime) {
        this.stepTime = stepTime;
    }

    public String getStepTimeUnit() {
        return stepTimeUnit;
    }

    public void setStepTimeUnit(String stepTimeUnit) {
        this.stepTimeUnit = stepTimeUnit;
    }
}
