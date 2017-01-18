// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

/**
 * @author mokous86@gmail.com create date: Dec 18, 2016
 *
 */
public enum ImageFilterTypeEnum {
    origin(0, "原始"), test(1, "测试demo");
    // sepia(1, "复古"), grayscale(2, "灰度"), contrast(3, "对比度"), invert(4, "反色"),
    // saturate(5, "饱和度"),
    // invertalpha(6, "透明度"), blur(7, "模糊");
    private int type;
    private String desc;

    private ImageFilterTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
