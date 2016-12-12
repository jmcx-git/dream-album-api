// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.model;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
public class MergeImgFileResp {
    private String localPath;
    private String urlPath;
    private boolean merged;
    private String errmsg;

    public MergeImgFileResp(String localPath, String urlPath) {
        super();
        this.localPath = localPath;
        this.urlPath = urlPath;
        this.merged = true;
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

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
