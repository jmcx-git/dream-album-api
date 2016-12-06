// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

import com.dreambox.core.DbStatus;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
public class UserAlbumItemInfo extends DbStatus {
    private int id;
    private int userAlbumId;
    private int albumId;
    private int albumItemId;
    private String userOriginImgUrl;// 用户上传的原始图片的地址
    private String previewImgUrl;// 用户编辑完后生成的预览图片地址
    private int rank; // 在album中所有图片的第几张
    private String editImgInfos;// json 【css arrtibute json object】
    private String editTextInfos;// json【css arrtibute json object】

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUserAlbumId() {
        return userAlbumId;
    }

    public void setUserAlbumId(int userAlbumId) {
        this.userAlbumId = userAlbumId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getAlbumItemId() {
        return albumItemId;
    }

    public void setAlbumItemId(int albumItemId) {
        this.albumItemId = albumItemId;
    }

    public String getUserOriginImgUrl() {
        return userOriginImgUrl;
    }

    public void setUserOriginImgUrl(String userOriginImgUrl) {
        this.userOriginImgUrl = userOriginImgUrl;
    }

}
