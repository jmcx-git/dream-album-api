// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.dreambox.core.utils;

import org.apache.log4j.Logger;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class NumberUtils {
    private static final Logger log = Logger.getLogger(NumberUtils.class);

    public static final float getFloat(String intValue, float defaultValue) {
        try {
            return Float.parseFloat(intValue);
        } catch (Exception e) {
            log.warn("Convert string " + intValue + " to float failed.");
        }
        return defaultValue;
    }
}
