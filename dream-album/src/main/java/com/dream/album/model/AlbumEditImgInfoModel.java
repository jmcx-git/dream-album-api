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
    private Float cssElmUserScaleX;
    private Float cssElmUserScaleY;

    public AlbumEditImgInfoModel() {
        super();
    }

    public AlbumEditImgInfoModel(String cssElmMoveX, String cssElmMoveY, String cssUploadImgInDeviceWidth,
            String cssUploadImgInDeviceHeight, String cssElmUserScaleX, String cssElmUserScaleY) {
        super();
        this.cssElmMoveX = cssElmMoveX == null ? 0 : Float.valueOf(cssElmMoveX).intValue();
        this.cssElmMoveY = cssElmMoveY == null ? 0 : Float.valueOf(cssElmMoveY).intValue();
        this.cssUploadImgInDeviceWidth = cssUploadImgInDeviceWidth == null ? 0 : Float.valueOf(
                cssUploadImgInDeviceWidth).intValue();
        this.cssUploadImgInDeviceHeight = cssUploadImgInDeviceHeight == null ? 0 : Float.valueOf(
                cssUploadImgInDeviceHeight).intValue();
        this.cssElmUserScaleX = cssElmUserScaleX == null ? 1 : Float.valueOf(cssElmUserScaleX);
        this.cssElmUserScaleY = cssElmUserScaleY == null ? 1 : Float.valueOf(cssElmUserScaleY);
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

    public Float getCssElmUserScaleX() {
        return cssElmUserScaleX;
    }

    public void setCssElmUserScaleX(Float cssElmUserScaleX) {
        this.cssElmUserScaleX = cssElmUserScaleX;
    }

    public Float getCssElmUserScaleY() {
        return cssElmUserScaleY;
    }

    public void setCssElmUserScaleY(Float cssElmUserScaleY) {
        this.cssElmUserScaleY = cssElmUserScaleY;
    }
}
