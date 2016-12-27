// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

import com.dreambox.core.dto.DbKey.UNIQUE_KEY;
import com.dreambox.core.dto.StatusSerializable;

/**
 * @author mokous86@gmail.com create date: 2016年12月27日
 *
 */
public class SmallAppDeveloperInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3074985105487504815L;

    private int id;
    @UNIQUE_KEY
    private String appId;
    private String secret;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
