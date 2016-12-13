package com.dream.album.model;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
public class AlbumEditImgInfoModel {
    private Integer cssElmMoveX;
    private Integer cssElmMoveY;
    private Integer cssUploadImgInDeviceWidth;
    private Integer cssUploadImgInDeviceHeight;
    private Integer cssElmUserScaleX;
    private Integer cssElmUserScaleY;

    public AlbumEditImgInfoModel() {
        super();
    }

    public AlbumEditImgInfoModel(Integer cssElmMoveX, Integer cssElmMoveY, Integer cssUploadImgInDeviceWidth,
            Integer cssUploadImgInDeviceHeight, Integer cssElmUserScaleX, Integer cssElmUserScaleY) {
        super();
        this.cssElmMoveX = cssElmMoveX;
        this.cssElmMoveY = cssElmMoveY;
        this.cssUploadImgInDeviceWidth = cssUploadImgInDeviceWidth;
        this.cssUploadImgInDeviceHeight = cssUploadImgInDeviceHeight;
        this.cssElmUserScaleX = cssElmUserScaleX;
        this.cssElmUserScaleY = cssElmUserScaleY;
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

    public Integer getCssUploadImgInDeviceWidth() {
        return cssUploadImgInDeviceWidth;
    }

    public void setCssUploadImgInDeviceWidth(Integer cssUploadImgInDeviceWidth) {
        this.cssUploadImgInDeviceWidth = cssUploadImgInDeviceWidth;
    }

    public Integer getCssUploadImgInDeviceHeight() {
        return cssUploadImgInDeviceHeight;
    }

    public void setCssUploadImgInDeviceHeight(Integer cssUploadImgInDeviceHeight) {
        this.cssUploadImgInDeviceHeight = cssUploadImgInDeviceHeight;
    }

    public Integer getCssElmUserScaleX() {
        return cssElmUserScaleX;
    }

    public void setCssElmUserScaleX(Integer cssElmUserScaleX) {
        this.cssElmUserScaleX = cssElmUserScaleX;
    }

    public Integer getCssElmUserScaleY() {
        return cssElmUserScaleY;
    }

    public void setCssElmUserScaleY(Integer cssElmUserScaleY) {
        this.cssElmUserScaleY = cssElmUserScaleY;
    }
}
