package com.dreambox.core.dto.album;

import com.dreambox.core.dto.StatusSerializable;

/**
 * 记录相册模版已创建记录信息的实体类
 * 
 * @author liuxinglong
 * @date 2016年12月6日
 */
public class AlbumTemplateCreatedInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5904681852115698635L;

    private int id;
    private String userId;
    private int templateId;
    /**
     * 模版操作信息json串
     */
    private String handleInfos;
    /**
     * 生成的h5 url
     */
    private String h5Url;
    /**
     * 生成的大图url
     */
    private String pngUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getHandleInfos() {
        return handleInfos;
    }

    public void setHandleInfos(String handleInfos) {
        this.handleInfos = handleInfos;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getPngUrl() {
        return pngUrl;
    }

    public void setPngUrl(String pngUrl) {
        this.pngUrl = pngUrl;
    }

    @Override
    public String toString() {
        return "AlbumTemplateCreatedInfo [id=" + id + ", userId=" + userId + ", templateId=" + templateId
                + ", handleInfos=" + handleInfos + ", h5Url=" + h5Url + ", pngUrl=" + pngUrl + "]";
    }
}
