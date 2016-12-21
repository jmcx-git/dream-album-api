// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

import java.util.List;

import com.dreambox.core.DbStatus;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
public class AlbumInfo extends DbStatus {
    /**
     * 
     */
    private static final long serialVersionUID = 7780536159988780040L;
    private int id;
    private String title;
    private String keyword;
    private int totalItems;
    private String cover;// 封面图
    private String previewImg;// 预览图

    private int collect;
    private List<AlbumItemInfo> albumItemList;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }

    public List<AlbumItemInfo> getAlbumItemList() {
        return albumItemList;
    }

    public void setAlbumItemList(List<AlbumItemInfo> albumItemList) {
        this.albumItemList = albumItemList;
    }

}
