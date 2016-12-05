// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class LocalRes {
    private String localRelativePath;
    private String localFullPath;
    private String url;

    public String getLocalRelativePath() {
        return localRelativePath;
    }

    public void setLocalRelativePath(String localRelativePath) {
        this.localRelativePath = localRelativePath;
    }

    public String getLocalFullPath() {
        return localFullPath;
    }

    public void setLocalFullPath(String localFullPath) {
        this.localFullPath = localFullPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LocalRes [localRelativePath=" + localRelativePath + ", localFullPath=" + localFullPath + ", url=" + url
                + "]";
    }
}
