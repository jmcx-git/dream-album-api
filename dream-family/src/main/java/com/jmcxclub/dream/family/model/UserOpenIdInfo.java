package com.jmcxclub.dream.family.model;

import java.io.Serializable;

public class UserOpenIdInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3828508543814492647L;
    private String openid;
    private String session_key;
    private Integer expires_in;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }


}
