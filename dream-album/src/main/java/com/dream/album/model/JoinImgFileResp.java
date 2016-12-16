// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.model;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
public class JoinImgFileResp {
    private String localPath;
    private String urlPath;
    private boolean joined;
    private String errmsg;

    public JoinImgFileResp(String localPath, String urlPath, boolean joined) {
        super();
        this.localPath = localPath;
        this.urlPath = urlPath;
        this.joined = joined;
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

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
