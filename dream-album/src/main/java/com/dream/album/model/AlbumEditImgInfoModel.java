package com.dream.album.model;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
public class AlbumEditImgInfoModel {
    private Integer cssElmMoveX;
    private Integer cssElmMoveY;
    private Integer cssElmWidth;
    private Integer cssElmHeight;
    private Integer cssElmRotate;

    public AlbumEditImgInfoModel() {
        super();
    }

    public AlbumEditImgInfoModel(Integer cssElmMoveX, Integer cssElmMoveY, Integer cssElmWidth, Integer cssElmHeight,
            Integer cssElmRotate) {
        super();
        this.cssElmMoveX = cssElmMoveX == null ? 0 : cssElmMoveX;
        this.cssElmMoveY = cssElmMoveY == null ? 0 : cssElmMoveY;
        this.cssElmWidth = cssElmWidth == null ? 0 : cssElmWidth;
        this.cssElmHeight = cssElmHeight == null ? 0 : cssElmHeight;
        this.cssElmRotate = cssElmRotate == null ? 0 : cssElmRotate;
    }

    public Integer getCssElmMoveX() {
        return cssElmMoveX;
    }

    public void setCssElmMoveX(Integer cssElmMoveX) {
        this.cssElmMoveX = cssElmMoveX;
    }

    public Integer getCssElmMoveY() {
        return cssElmMoveY;
    }

    public void setCssElmMoveY(Integer cssElmMoveY) {
        this.cssElmMoveY = cssElmMoveY;
    }

    public Integer getCssElmWidth() {
        return cssElmWidth;
    }

    public void setCssElmWidth(Integer cssElmWidth) {
        this.cssElmWidth = cssElmWidth;
    }

    public Integer getCssElmHeight() {
        return cssElmHeight;
    }

    public void setCssElmHeight(Integer cssElmHeight) {
        this.cssElmHeight = cssElmHeight;
    }

    public Integer getCssElmRotate() {
        return cssElmRotate;
    }

    public void setCssElmRotate(Integer cssElmRotate) {
        this.cssElmRotate = cssElmRotate;
    }
}
