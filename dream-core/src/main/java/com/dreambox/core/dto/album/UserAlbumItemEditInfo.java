package com.dreambox.core.dto.album;

import com.dreambox.core.DbStatus;

/**
 * @author liuxinglong
 * @date 2016年12月29日
 */
public class UserAlbumItemEditInfo extends DbStatus {

    /**
     * 
     */
    private static final long serialVersionUID = 8170629495298871934L;

    private int id;
    private int userAlbumItemId;
    private int rank;
    private String userOriginImgUrl;
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

    public int getUserAlbumItemId() {
        return userAlbumItemId;
    }

    public void setUserAlbumItemId(int userAlbumItemId) {
        this.userAlbumItemId = userAlbumItemId;
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

    public String getUserOriginImgUrl() {
        return userOriginImgUrl;
    }

    public void setUserOriginImgUrl(String userOriginImgUrl) {
        this.userOriginImgUrl = userOriginImgUrl;
    }
}
