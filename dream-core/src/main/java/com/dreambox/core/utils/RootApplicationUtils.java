package com.dreambox.core.utils;

import org.apache.commons.lang3.StringUtils;

public class RootApplicationUtils {

    public static String parseShortDesc(String content) {
        String shortDesc = null;
        if (!StringUtils.isEmpty(content)) {
            int endIndex = -1;
            if (content.indexOf(".") != -1) {
                endIndex = content.indexOf(".");
            }
            if (content.indexOf("!") != -1) {
                if (endIndex != -1) {
                    endIndex = endIndex < content.indexOf("!") ? endIndex : content.indexOf("!");
                } else {
                    endIndex = content.indexOf("!");
                }
            }
            if (content.indexOf("?") != -1) {
                if (endIndex != -1) {
                    endIndex = endIndex < content.indexOf("?") ? endIndex : content.indexOf("?");
                } else {
                    endIndex = content.indexOf("?");
                }
            }
            if (content.indexOf("。") != -1) {
                if (endIndex != -1) {
                    endIndex = endIndex < content.indexOf("。") ? endIndex : content.indexOf("。");
                } else {
                    endIndex = content.indexOf("。");
                }
            }
            if (content.indexOf("！") != -1) {
                if (endIndex != -1) {
                    endIndex = endIndex < content.indexOf("！") ? endIndex : content.indexOf("！");
                } else {
                    endIndex = content.indexOf("！");
                }
            }
            if (content.indexOf("？") != -1) {
                if (endIndex != -1) {
                    endIndex = endIndex < content.indexOf("？") ? endIndex : content.indexOf("？");
                } else {
                    endIndex = content.indexOf("？");
                }
            }
            shortDesc = content.substring(0, endIndex == -1 ? content.length() : endIndex);
            // remove \r\n \n
            shortDesc = StringUtils.replace(shortDesc, "\r\n", "");
            shortDesc = StringUtils.replace(shortDesc, "\n", "");
            if (shortDesc.length() >= 255) {
                shortDesc = shortDesc.substring(0, 251) + "...";
            }
        }
        return shortDesc;
    }

}
