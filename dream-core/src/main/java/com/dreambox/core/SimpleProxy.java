package com.dreambox.core;

import java.io.Serializable;

/**
 * Created by zhouyanhui on 3/29/16.
 */
public class SimpleProxy implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3704074357762710125L;
    private String ip;
    private Integer port;

    public SimpleProxy(String proxyIp, Integer proxyPort) {
        this.ip = proxyIp;
        this.port = proxyPort;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Proxy{" + "ip='" + ip + '\'' + ", port=" + port + '}';
    }
}
