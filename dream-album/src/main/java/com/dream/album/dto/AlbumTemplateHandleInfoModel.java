package com.dream.album.dto;

import java.util.Map;


/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
public class AlbumTemplateHandleInfo {

    /**
     * 相册模版的标题
     */
    private Map<String, AlbumTemplatePageInfo> handleInfo;

    public Map<String, AlbumTemplatePageInfo> getHandleInfo() {
        return handleInfo;
    }

    public void setHandleInfo(Map<String, AlbumTemplatePageInfo> handleInfo) {
        this.handleInfo = handleInfo;
    }

    @Override
    public String toString() {
        return "AlbumTemplateHandleInfo [handleInfo=" + handleInfo + "]";
    }
}
