package com.dream.album.dto;

import java.util.Map;


/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
public class AlbumTemplateHandleInfoModel {

    /**
     * 相册模版的操作信息
     */
    private Map<String, AlbumTemplatePageInfoModel> handleInfo;

    public Map<String, AlbumTemplatePageInfoModel> getHandleInfo() {
        return handleInfo;
    }

    public void setHandleInfo(Map<String, AlbumTemplatePageInfoModel> handleInfo) {
        this.handleInfo = handleInfo;
    }

    @Override
    public String toString() {
        return "AlbumTemplateHandleInfo [handleInfo=" + handleInfo + "]";
    }
}
