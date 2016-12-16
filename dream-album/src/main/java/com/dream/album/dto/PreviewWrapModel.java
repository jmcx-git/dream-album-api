package com.dream.album.dto;

import java.util.List;

/**
 * @author liuxinglong
 * @date 2016年12月15日
 */
public class PreviewWrapModel {
    private List<String> loopPreImgs;
    private String bigPreImg;

    public List<String> getLoopPreImgs() {
        return loopPreImgs;
    }

    public void setLoopPreImgs(List<String> loopPreImgs) {
        this.loopPreImgs = loopPreImgs;
    }

    public String getBigPreImg() {
        return bigPreImg;
    }

    public void setBigPreImg(String bigPreImg) {
        this.bigPreImg = bigPreImg;
    }
}
