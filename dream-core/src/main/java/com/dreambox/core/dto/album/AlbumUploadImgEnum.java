package com.dreambox.core.dto.album;

/**
 * @author liuxinglong
 * @date 2016年12月21日
 */
public enum AlbumUploadImgEnum {
    UPLOAD_IMG(0, "图片上传"), UPLOAD_NO_IMG(1, "空图上传");

    private int status;
    private String desc;

    private AlbumUploadImgEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}
