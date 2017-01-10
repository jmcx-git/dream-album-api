// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
public class UploadFileSaveResp {
    private String localPath;
    private String urlPath;
    private boolean saved;
    private String errmsg;

    public UploadFileSaveResp(String localPath, String urlPath) {
        super();
        this.localPath = localPath;
        this.urlPath = urlPath;
        this.saved = true;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
