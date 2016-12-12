package com.dream.album.model;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
public class AlbumEditImgInfoModel {

    private Integer cssMoveX;
    private Integer cssMoveY;
    private Integer cssRotate;
    private Integer cssImgWidth;
    private Integer cssImgHeight;

    public AlbumEditImgInfoModel() {
        super();
    }

    public AlbumEditImgInfoModel(Integer cssMoveX, Integer cssMoveY, Integer cssRotate, Integer cssImgWidth,
            Integer cssImgHeight) {
        super();
        this.cssMoveX = cssMoveX == null ? 0 : cssMoveX;
        this.cssMoveY = cssMoveY == null ? 0 : cssMoveY;
        this.cssRotate = cssRotate == null ? 0 : cssRotate;
        this.cssImgWidth = cssImgWidth == null ? 0 : cssImgWidth;
        this.cssImgHeight = cssImgHeight == null ? 0 : cssImgHeight;
    }

    public Integer getCssMoveX() {
        return cssMoveX;
    }

    public void setCssMoveX(Integer cssMoveX) {
        this.cssMoveX = cssMoveX;
    }

    public Integer getCssMoveY() {
        return cssMoveY;
    }

    public void setCssMoveY(Integer cssMoveY) {
        this.cssMoveY = cssMoveY;
    }

    public Integer getCssRotate() {
        return cssRotate;
    }

    public void setCssRotate(Integer cssRotate) {
        this.cssRotate = cssRotate;
    }

    public Integer getCssImgWidth() {
        return cssImgWidth;
    }

    public void setCssImgWidth(Integer cssImgWidth) {
        this.cssImgWidth = cssImgWidth;
    }

    public Integer getCssImgHeight() {
        return cssImgHeight;
    }

    public void setCssImgHeight(Integer cssImgHeight) {
        this.cssImgHeight = cssImgHeight;
    }
}
