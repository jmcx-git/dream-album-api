package com.dream.album.dto;

import java.util.Map;


/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
public class AlbumTemplate {

    /**
     * 相册模版的标题
     */
    private String title;
    /**
     * 相册模版的展示略缩图
     */
    private String preImg;
    /**
     * 相册模版每页的背景图片
     */
    private Map<String, String> backgoundImgs;
    /**
     * 相册模版每页的信息集
     */
    private Map<String, AlbumTemplatePageInfo> pageInfos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreImg() {
        return preImg;
    }

    public void setPreImg(String preImg) {
        this.preImg = preImg;
    }

    public Map<String, String> getBackgoundImgs() {
        return backgoundImgs;
    }

    public void setBackgoundImgs(Map<String, String> backgoundImgs) {
        this.backgoundImgs = backgoundImgs;
    }

    public Map<String, AlbumTemplatePageInfo> getPageInfos() {
        return pageInfos;
    }

    public void setPageInfos(Map<String, AlbumTemplatePageInfo> pageInfos) {
        this.pageInfos = pageInfos;
    }

    @Override
    public String toString() {
        return "AlbumTemplate [title=" + title + ", preImg=" + preImg + ", backgoundImgs=" + backgoundImgs
                + ", pageInfos=" + pageInfos + "]";
    }
}
