package com.dreambox.core;
// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class KeyId {
    private String key;
    private long id;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "KeyId [key=" + key + ", id=" + id + "]";
    }
}
