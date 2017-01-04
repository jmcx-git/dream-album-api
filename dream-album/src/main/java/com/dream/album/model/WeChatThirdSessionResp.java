// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.model;

/**
 * @author mokous86@gmail.com create date: Dec 30, 2016
 *
 */
public class WeChatThirdSessionResp {
    private String openId;
    private String sessionKey;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}
