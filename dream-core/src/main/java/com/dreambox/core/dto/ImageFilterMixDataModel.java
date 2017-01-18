package com.dreambox.core.dto;

/**
 * @author liuxinglong
 * @date 2017年1月9日
 */
public class ImageFilterMixDataModel {
    // grayscale:0.3,sepic:0.3,saturate:1.3
    // 灰度
    private float grayscale;
    // 复古
    private float sepia;
    // 饱和度
    private float saturate;

    public float getGrayscale() {
        return grayscale;
    }

    public void setGrayscale(float grayscale) {
        this.grayscale = grayscale;
    }

    public float getSepia() {
        return sepia;
    }

    public void setSepia(float sepia) {
        this.sepia = sepia;
    }

    public float getSaturate() {
        return saturate;
    }

    public void setSaturate(float saturate) {
        this.saturate = saturate;
    }
}
