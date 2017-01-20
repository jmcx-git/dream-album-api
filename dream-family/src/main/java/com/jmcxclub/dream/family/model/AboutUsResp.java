// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dreambox.web.utils.GsonUtils;

/**
 * @author mokous86@gmail.com create date: Jan 20, 2017
 *
 */
public class AboutUsResp implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3298197307282539699L;
    private String solgan = "";
    private String officialServiceQrCode = "https://cdn.mokous.com/static/qrcode/official_service_qucode_170127.jpeg";// 服务群
    private String mpQrCode = "https://cdn.mokous.com/static/qrcode/official_service_qucode_170127.jpeg";// 公方号
    private List<String> bottomDesctions = new ArrayList<String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 6253738440098832074L;

        {
            add("官方QQ群:612883308");
        }
    };

    public static void main(String[] args) {
        System.out.println(GsonUtils.toJson(new AboutUsResp()));
    }

    public String getSolgan() {
        return solgan;
    }

    public void setSolgan(String solgan) {
        this.solgan = solgan;
    }

    public String getOfficialServiceQrCode() {
        return officialServiceQrCode;
    }

    public void setOfficialServiceQrCode(String officialServiceQrCode) {
        this.officialServiceQrCode = officialServiceQrCode;
    }

    public String getMpQrCode() {
        return mpQrCode;
    }

    public void setMpQrCode(String mpQrCode) {
        this.mpQrCode = mpQrCode;
    }

    public List<String> getBottomDesctions() {
        return bottomDesctions;
    }

    public void setBottomDesctions(List<String> bottomDesctions) {
        this.bottomDesctions = bottomDesctions;
    }
}
