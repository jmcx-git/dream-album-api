package com.dream.album.dto;

/**
 * @author liuxinglong
 * @date 2016年12月9日
 */
public class MyAlbumModel {
    private int albumId;// 相册
    private String title;
    private String cover;// 封面图
    private String previewImg;// 预览图

    private int userAlbumId;// 用户相册Id
    private int complete;// 是否生成了相册
    private String productImg;// 在用户户生成完相册后根据信息生成预览图


    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getUserAlbumId() {
        return userAlbumId;
    }

    public void setUserAlbumId(int userAlbumId) {
        this.userAlbumId = userAlbumId;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }
}
