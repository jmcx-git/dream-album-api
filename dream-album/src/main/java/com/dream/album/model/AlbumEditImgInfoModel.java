package com.dream.album.model;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
public class AlbumEditImgInfoModel {

    private int positionX;
    private int positionY;
    private int rotate;
    private int width;
    private int height;

    public AlbumEditImgInfoModel() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AlbumEditImgInfoModel(int positionX, int positionY, int rotate, int width, int height) {
        super();
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotate = rotate;
        this.width = width;
        this.height = height;
    }


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

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
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
}
