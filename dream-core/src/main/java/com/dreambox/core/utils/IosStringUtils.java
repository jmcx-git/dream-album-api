package com.dreambox.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhouyanhui on 11/24/15.
 */
public class IosStringUtils {
    private static final Pattern chinesePattern = Pattern.compile("[\u4e00-\u9fa5]");

    public static boolean containsChinese(String str) {
        if(StringUtils.isBlank(str)){
            return false;
        }
        Matcher m = chinesePattern.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
