// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public enum ActivityStepEnum {
    INIT(0, "未开始"), ING(1, "进行中"), AUDIT(2, "投票审计中"), FINISH(3, "结束");
    private int step;
    private String desc;

    private ActivityStepEnum(int step, String desc) {
        this.step = step;
        this.desc = desc;
    }

    public int getStep() {
        return step;
    }

    public String getDesc() {
        return desc;
    }
}
