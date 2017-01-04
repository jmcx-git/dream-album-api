package com.dreambox.core.dto.album;

import com.dreambox.core.DbStatus;

/**
 * @author liuxinglong
 * @date 2016年12月29日
 */
public class AlbumItemEditInfo extends DbStatus {

    /**
     * 
     */
    private static final long serialVersionUID = -6441469994474946409L;
    private int id;
    private int albumItemId;
    private int rank;
    private String editImgUrl;
    private int cssElmMoveX;
    private int cssElmMoveY;
    private int cssElmWidth;
    private int cssElmHeight;
    private int cssElmRotate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCssElmMoveX() {
        return cssElmMoveX;
    }

    public void setCssElmMoveX(int cssElmMoveX) {
        this.cssElmMoveX = cssElmMoveX;
    }

    public int getCssElmMoveY() {
        return cssElmMoveY;
    }

    public void setCssElmMoveY(int cssElmMoveY) {
        this.cssElmMoveY = cssElmMoveY;
    }

    public int getCssElmWidth() {
        return cssElmWidth;
    }

    public void setCssElmWidth(int cssElmWidth) {
        this.cssElmWidth = cssElmWidth;
    }

    public int getCssElmHeight() {
        return cssElmHeight;
    }

    public void setCssElmHeight(int cssElmHeight) {
        this.cssElmHeight = cssElmHeight;
    }

    public int getCssElmRotate() {
        return cssElmRotate;
    }

    public void setCssElmRotate(int cssElmRotate) {
        this.cssElmRotate = cssElmRotate;
    }

    public int getAlbumItemId() {
        return albumItemId;
    }

    public void setAlbumItemId(int albumItemId) {
        this.albumItemId = albumItemId;
    }

    public String getEditImgUrl() {
        return editImgUrl;
    }

    public void setEditImgUrl(String editImgUrl) {
        this.editImgUrl = editImgUrl;
    }
}
