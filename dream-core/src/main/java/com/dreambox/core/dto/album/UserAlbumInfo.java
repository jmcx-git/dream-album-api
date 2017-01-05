// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

import com.dreambox.core.DbField.SELECT_ALL_KEY;
import com.dreambox.core.DbStatus;
import com.dreambox.core.DbField.ZERO_ENABLE;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016 用户生成相册信息表
 */
public class UserAlbumInfo extends DbStatus {
    /**
     * 
     */
    private static final long serialVersionUID = -1929636011391406410L;
    private int id;
    private int userId;// 用户id
    private int albumId;// 选用的相册模板
    private String title;// 用户相册标题
    @SELECT_ALL_KEY
    @ZERO_ENABLE
    private int complete;// 是否生成了相册
    private String previewImg;// 在用户户生成完相册后根据信息生成预览图
    private String coverImg;// 在用户户生成完相册后根据封面信息生成的封面图

    public UserAlbumInfo() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }
}
