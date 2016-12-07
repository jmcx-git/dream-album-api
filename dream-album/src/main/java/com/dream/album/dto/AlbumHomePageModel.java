package com.dream.album.dto;

import java.util.ArrayList;
import java.util.List;

import com.dreambox.core.dto.album.AlbumInfo;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
public class AlbumHomePageModel {
    private List<AlbumInfo> albumList;
    private List<String> keywords;

    public AlbumHomePageModel() {
        List<String> list = new ArrayList<String>();
        list.add("圣诞");
        list.add("亲子");
        list.add("搞笑");
        list.add("cool");
        this.keywords = list;
    }


    public List<AlbumInfo> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<AlbumInfo> albumList) {
        this.albumList = albumList;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
