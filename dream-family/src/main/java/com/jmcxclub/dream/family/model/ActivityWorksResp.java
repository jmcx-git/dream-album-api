// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.jmcxclub.dream.family.dto.ActivityWorksInfo;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
public class ActivityWorksResp {
    private int id;
    private int rank;
    private int votes;
    private int type;// 作品类型 0：非视频 1:视频 2:音频
    private String cover;// 如果是以type==0参赛,则此处和resourceUrl一样,否则此值为对应的资源封面
    private String resourceUrl;// 如果是以type==0参赛,则此处和cover一样,否则此值为对应的资源地址
    private List<String> illustrations;
    private Long durations;// 参赛作品如果是视频,音频则为其时长
    private String solgan;// 参赛作品口号
    private String desc;// 参赛作品描述

    public ActivityWorksResp(ActivityWorksInfo info, int rank, int votes) {
        try {
            BeanUtils.copyProperties(this, info);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        ContentDescUtils.buildCoverAndIllustrations(this, info);
        this.rank = rank;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
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

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Long getDurations() {
        return durations;
    }

    public void setDurations(Long durations) {
        this.durations = durations;
    }

    public String getSolgan() {
        return solgan;
    }

    public void setSolgan(String solgan) {
        this.solgan = solgan;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(List<String> illustrations) {
        this.illustrations = illustrations;
    }
}
