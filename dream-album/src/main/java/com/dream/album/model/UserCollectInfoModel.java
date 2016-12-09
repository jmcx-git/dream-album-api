package com.dream.album.model;

import java.util.List;

import com.dreambox.core.dto.album.AlbumInfo;

public class UserCollectInfoModel {

    private List<AlbumInfo> collectList;
    private long count;

    public List<AlbumInfo> getCollectList() {
        return collectList;
    }

    public void setCollectList(List<AlbumInfo> collectList) {
        this.collectList = collectList;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


}
