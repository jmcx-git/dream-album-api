// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.model;

import java.util.List;

import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
public class UserMakeAlbumInfo {
    private UserAlbumInfo userAlbumInfo;
    private List<AlbumItemInfo> albumItemInfos;
    private List<UserAlbumItemInfo> userAlbumItemInfos;

    public UserMakeAlbumInfo(UserAlbumInfo userAlbumInfo, List<AlbumItemInfo> albumItemInfos,
            List<UserAlbumItemInfo> userAlbumItemInfos) {
        super();
        this.userAlbumInfo = userAlbumInfo;
        this.albumItemInfos = albumItemInfos;
        this.userAlbumItemInfos = userAlbumItemInfos;
    }

    public UserAlbumInfo getUserAlbumInfo() {
        return userAlbumInfo;
    }

    public void setUserAlbumInfo(UserAlbumInfo userAlbumInfo) {
        this.userAlbumInfo = userAlbumInfo;
    }

    public List<AlbumItemInfo> getAlbumItemInfos() {
        return albumItemInfos;
    }

    public void setAlbumItemInfos(List<AlbumItemInfo> albumItemInfos) {
        this.albumItemInfos = albumItemInfos;
    }

    public List<UserAlbumItemInfo> getUserAlbumItemInfos() {
        return userAlbumItemInfos;
    }

    public void setUserAlbumItemInfos(List<UserAlbumItemInfo> userAlbumItemInfos) {
        this.userAlbumItemInfos = userAlbumItemInfos;
    }

}
