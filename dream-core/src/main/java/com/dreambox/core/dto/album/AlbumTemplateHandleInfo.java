package com.dreambox.core.dto.album;

import com.dreambox.core.dto.StatusSerializable;

/**
 * 记录相册模版操作记录信息的实体类
 * 
 * @author liuxinglong
 * @date 2016年12月6日
 */
public class AlbumTemplateHandleInfo extends StatusSerializable {

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
     * 操作到第几步
     */
    private int step;

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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "AlbumTemplateHandleInfo [id=" + id + ", userId=" + userId + ", templateId=" + templateId
                + ", handleInfos=" + handleInfos + ", step=" + step + "]";
    }
}
