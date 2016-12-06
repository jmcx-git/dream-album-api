package com.dreambox.core.dto.album;

import com.dreambox.core.dto.StatusSerializable;

/**
 * 相册模版的初始化信息实体类
 * 
 * @author liuxinglong
 * @date 2016年12月6日
 */
public class AlbumTemplateInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5904681852115698635L;

    private int id;
    private String title;
    private String preImg;
    /**
     * json串的操作信息 对应的是AlbumTemplateModel的每页背景的Map<String,String>的json
     */
    private String backgoundImgs;
    /**
     * json串的操作信息
     * 对应的是AlbumTemplateModel的每页的Map<String,AlbumTemplatePageInfoModel>的json
     */
    private String pageInfos;

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

    public String getPreImg() {
        return preImg;
    }

    public void setPreImg(String preImg) {
        this.preImg = preImg;
    }

    public String getBackgoundImgs() {
        return backgoundImgs;
    }

    public void setBackgoundImgs(String backgoundImgs) {
        this.backgoundImgs = backgoundImgs;
    }

    public String getPageInfos() {
        return pageInfos;
    }

    public void setPageInfos(String pageInfos) {
        this.pageInfos = pageInfos;
    }

    @Override
    public String toString() {
        return "AlbumTemplateInfo [id=" + id + ", title=" + title + ", preImg=" + preImg + ", backgoundImgs="
                + backgoundImgs + ", pageInfos=" + pageInfos + "]";
    }
}
