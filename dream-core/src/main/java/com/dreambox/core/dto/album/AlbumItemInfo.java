// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

import com.dreambox.core.DbStatus;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
public class AlbumItemInfo extends DbStatus {
    /**
     * 
     */
    private static final long serialVersionUID = -4058233262573193853L;
    private int id;
    private int albumId;
    private String editImgUrl;// 可编辑图片区域为透明区域人图片
    private String previewImgUrl;// 预览图
    private String shadowImgUrl;// 阴影图
    private int imgWidth;// 模版图宽
    private int imgHeight;// 模版图高
    private int rank; // 在album中所有图片的第几张
    private int editCount;// 可编辑元素个数
    private String editImgInfos;// json 【css arrtibute json object】
    private String editTextInfos;// json【css arrtibute json object】

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getEditImgUrl() {
        return editImgUrl;
    }

    public void setEditImgUrl(String editImgUrl) {
        this.editImgUrl = editImgUrl;
    }

    public String getPreviewImgUrl() {
        return previewImgUrl;
    }

    public void setPreviewImgUrl(String previewImgUrl) {
        this.previewImgUrl = previewImgUrl;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getEditImgInfos() {
        return editImgInfos;
    }

    public void setEditImgInfos(String editImgInfos) {
        this.editImgInfos = editImgInfos;
    }

    public String getEditTextInfos() {
        return editTextInfos;
    }

    public void setEditTextInfos(String editTextInfos) {
        this.editTextInfos = editTextInfos;
    }

    public int getEditCount() {
        return editCount;
    }

    public void setEditCount(int editCount) {
        this.editCount = editCount;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getShadowImgUrl() {
        return shadowImgUrl;
    }

    public void setShadowImgUrl(String shadowImgUrl) {
        this.shadowImgUrl = shadowImgUrl;
    }
}
