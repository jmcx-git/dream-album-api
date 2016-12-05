// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.dto;


/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class SortedInfo extends StatusSerializable implements Comparable<SortedInfo> {

    /**
     * 
     */
    private static final long serialVersionUID = -4739260111864630415L;

    @Override
    public int compareTo(SortedInfo o) {
        long value = (o.getUpdateTime().getTime() - this.getUpdateTime().getTime());
        return value > 0 ? 1 : -1;
    }

}
