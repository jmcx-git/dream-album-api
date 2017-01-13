// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dto;

import com.dreambox.core.DbField.ZERO_ENABLE;
import com.dreambox.core.dto.StatusSerializable;
import com.dreambox.core.dto.DbKey.UNIQUE_KEY;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public class ActivityUserPrizeInfo extends StatusSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3459396941294763536L;
    private int id;
    @UNIQUE_KEY
    private int activityId;
    @UNIQUE_KEY
    private int worksId;
    private int activityPrizeId;// 中奖描述 reference ActivityPrizeInfo.id
    private int prizeId;// reference PrizeInfo
    private int userId;
    @ZERO_ENABLE
    private int build;// 消息是否已构建 reference NoticeBuildEnum

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(int prizeId) {
        this.prizeId = prizeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWorksId() {
        return worksId;
    }

    public void setWorksId(int worksId) {
        this.worksId = worksId;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    public int getActivityPrizeId() {
        return activityPrizeId;
    }

    public void setActivityPrizeId(int activityPrizeId) {
        this.activityPrizeId = activityPrizeId;
    }
}
