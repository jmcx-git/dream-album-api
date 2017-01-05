// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

import com.dreambox.core.dto.DbKey.UNIQUE_KEY;
import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: Jan 5, 2017
 *
 */
public class AlbumCoverItemInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2793195496128618271L;
    private int id;
    @UNIQUE_KEY
    private int albumId;
    private int coverAlbumItemId;
    private String editImgUrl;// 可编辑图片区域为透明区域人图片
    private String previewImgUrl;// 预览图
    private String shadowImgUrl;// 阴影图

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoverAlbumItemId() {
        return coverAlbumItemId;
    }

    public void setCoverAlbumItemId(int coverAlbumItemId) {
        this.coverAlbumItemId = coverAlbumItemId;
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

    public String getShadowImgUrl() {
        return shadowImgUrl;
    }

    public void setShadowImgUrl(String shadowImgUrl) {
        this.shadowImgUrl = shadowImgUrl;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
