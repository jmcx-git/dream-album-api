package com.dream.album.dto;

/**
 * 相册模版 每一页包含的数据信息
 * 
 * @author liuxinglong
 * @date 2016年12月6日
 */
public class AlbumTemplatePageInfo {

    /**
     * 需要嵌入图片的区域坐标x
     */
    private int positionX;
    /**
     * 需要嵌入图片的区域坐标y
     */
    private int positionY;
    /**
     * 需要嵌入图片的区域旋转角度
     */
    private int rorate;
    /**
     * 需要嵌入图片的区域宽度
     */
    private int width;
    /**
     * 需要嵌入图片的区域高度
     */
    private int height;
    /**
     * 嵌入的图片地址
     */
    private String img;

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getRorate() {
        return rorate;
    }

    public void setRorate(int rorate) {
        this.rorate = rorate;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "AlbumTemplatePageInfo [positionX=" + positionX + ", positionY=" + positionY + ", rorate=" + rorate
                + ", width=" + width + ", height=" + height + ", img=" + img + "]";
    }
}
