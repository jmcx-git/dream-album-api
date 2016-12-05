// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class PromoteUtils {
    public static String chechPromoteTime(String st) {
        if (StringUtils.isEmpty(st)) {
            return "无效的时间";
        }
        try {
            if (DateUtils.beforeNow(DateUtils.parseDateStr(st))) {
                return "轮播图开始时间不能小于当前时间";
            }
        } catch (Exception e) {
            return "无效的时间";
        }
        return "";
    }

    public static String checkStEt(String st, String et) {
        if (StringUtils.isEmpty(et) || StringUtils.isEmpty(st)) {
            return "无效的时间";
        }
        try {
            if (DateUtils.parseDateStr(et).before(DateUtils.parseDateStr(st))) {
                return "轮播图结束时间不能早于开始时间";
            }
        } catch (Exception e) {
            return "无效的时间";
        }
        return "";
    }

}
