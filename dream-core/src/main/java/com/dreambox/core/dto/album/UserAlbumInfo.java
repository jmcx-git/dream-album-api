// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

import com.dreambox.core.DbStatus;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016 用户生成相册信息表
 */
public class UserAlbumInfo extends DbStatus {
    private int id;
    private int userId;// 用户id
    private int albumId;// 选用的相册模板
    private int step;// 当前操作到第几步
    private int complete;// 是否生成了相册
    private String priviewImg;// 在用户户生成完相册后根据信息生成预览图

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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public String getPriviewImg() {
        return priviewImg;
    }

    public void setPriviewImg(String priviewImg) {
        this.priviewImg = priviewImg;
    }

}
