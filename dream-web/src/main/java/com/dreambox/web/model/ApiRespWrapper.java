// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.dreambox.web.model;

import java.io.Serializable;

/**
 * @author luofei@refanqie.com (Your Name Here)
 * 
 */
public class ApiRespWrapper<G> implements Serializable {

    private static final long serialVersionUID = -5918416727477489899L;
    private int status;
    private String message;
    private G data;

    public ApiRespWrapper(int status, String message, G data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiRespWrapper(int status, String message) {
        super();
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public ApiRespWrapper(G data) {
        super();
        this.status = 0;
        this.message = "ok";
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiRespWrapper [status=" + status + ", message=" + message + ", data=" + data + "]";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public G getData() {
        return data;
    }

    public void setData(G data) {
        this.data = data;
    }
}
