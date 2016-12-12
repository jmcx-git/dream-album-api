package com.dream.album.model;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
public class AlbumEditImgInfoModel {

    private Integer cssElmMoveX;
    private Integer cssElmMoveY;
    private Integer cssElmRotate;
    private Integer cssElmWidth;
    private Integer cssElmHeight;

    public AlbumEditImgInfoModel() {
        super();
    }

    public AlbumEditImgInfoModel(Integer cssElmMoveX, Integer cssElmMoveY, Integer cssElmRotate, Integer cssElmWidth,
            Integer cssElmHeight) {
        super();
        this.cssElmMoveX = cssElmMoveX == null ? 0 : cssElmMoveX;
        this.cssElmMoveY = cssElmMoveY == null ? 0 : cssElmMoveY;
        this.cssElmRotate = cssElmRotate == null ? 0 : cssElmRotate;
        this.cssElmWidth = cssElmWidth == null ? 0 : cssElmWidth;
        this.cssElmHeight = cssElmHeight == null ? 0 : cssElmHeight;
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

    public Integer getCssElmRotate() {
        return cssElmRotate;
    }

    public void setCssElmRotate(Integer cssElmRotate) {
        this.cssElmRotate = cssElmRotate;
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
}
