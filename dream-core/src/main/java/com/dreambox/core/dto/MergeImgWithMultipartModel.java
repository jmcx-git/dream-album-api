package com.dreambox.core.dto;

import com.dreambox.core.dto.album.UserAlbumItemEditInfo;

/**
 * @author liuxinglong
 * @date 2016年12月29日
 */
public class MergeImgWithMultipartModel {

    private String path;
    private UserAlbumItemEditInfo userAlbumItemEditInfo;
    private boolean clipDefault;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UserAlbumItemEditInfo getUserAlbumItemEditInfo() {
        return userAlbumItemEditInfo;
    }

    public void setUserAlbumItemEditInfo(UserAlbumItemEditInfo userAlbumItemEditInfo) {
        this.userAlbumItemEditInfo = userAlbumItemEditInfo;
    }

    public boolean isClipDefault() {
        return clipDefault;
    }

    public void setClipDefault(boolean clipDefault) {
        this.clipDefault = clipDefault;
    }
}
