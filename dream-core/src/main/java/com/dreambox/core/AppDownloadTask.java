package com.dreambox.core;

import java.io.Serializable;

import com.dreambox.web.model.ApiRespWrapper;

/**
 * Created by zhouyanhui on 3/10/16.
 */
public class AppDownloadTask implements Serializable, Comparable<AppDownloadTask> {
    private static final long serialVersionUID = -7538051642597280640L;
    private String appleId;
    private String passwd;
    private SimpleProxy proxy;
    private Integer taskId;
    private boolean downloadIpa;
    private String appId;
    private String country;


    // for qiniu upload
    private String sinfFileQiniuKey;
    private String artworkFileQiniuKey;
    private String pureIpaFileQiniuKey;
    private String metadataFileQiniuKey;

    // 因为需要CDN回源，所以此处需由任务分发者指定具体要存储的文件名
    private String sinfName;
    private String artworkFileName;
    private String pureIpaFileName;
    private String metadataFileName;

    private String processInfo;
    private transient volatile AppDownloadTaskStatus status;

    private long downloadTimeMillis;
    private long uploadTimeMillis;

    private long downloadTaskStartTimeMillis;
    private long uploadTaskStartTimeMillis;

    // callback
    private String sinfLocalLocation;
    private String artworkLocalLocation;
    private String pureIpaLocalLocation;
    private String metadataLocalLocation;

    // callback
    private String ipaMd5;
    // callback
    private ApiRespWrapper<Boolean> uploadPureIpaResp;
    private ApiRespWrapper<Boolean> uploadSinfResp;
    private ApiRespWrapper<Boolean> uploadMetadataResp;
    private ApiRespWrapper<Boolean> uploadArtworkResp;


    public String getAppleId() {
        return appleId;
    }

    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public SimpleProxy getProxy() {
        return proxy;
    }

    public void setProxy(SimpleProxy proxy) {
        this.proxy = proxy;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setDownloadIpa(boolean downloadIpa) {
        this.downloadIpa = downloadIpa;
    }

    public boolean isDownloadIpa() {
        return downloadIpa;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public AppDownloadTaskStatus getStatus() {
        return status;
    }

    public void setStatus(AppDownloadTaskStatus status) {
        this.status = status;
    }

    @Override
    public int compareTo(AppDownloadTask o) {
        if (o == null)
            return 1;
        return taskId.compareTo(o.taskId);
    }

    public String getSinfFileQiniuKey() {
        return sinfFileQiniuKey;
    }

    public void setSinfFileQiniuKey(String sinfFileQiniuKey) {
        this.sinfFileQiniuKey = sinfFileQiniuKey;
    }

    public String getArtworkFileQiniuKey() {
        return artworkFileQiniuKey;
    }

    public void setArtworkFileQiniuKey(String artworkFileQiniuKey) {
        this.artworkFileQiniuKey = artworkFileQiniuKey;
    }

    public String getPureIpaFileQiniuKey() {
        return pureIpaFileQiniuKey;
    }

    public void setPureIpaFileQiniuKey(String pureIpaFileQiniuKey) {
        this.pureIpaFileQiniuKey = pureIpaFileQiniuKey;
    }

    public String getMetadataFileQiniuKey() {
        return metadataFileQiniuKey;
    }

    public void setMetadataFileQiniuKey(String metadataFileQiniuKey) {
        this.metadataFileQiniuKey = metadataFileQiniuKey;
    }

    public String getSinfName() {
        return sinfName;
    }

    public void setSinfName(String sinfName) {
        this.sinfName = sinfName;
    }

    public String getArtworkFileName() {
        return artworkFileName;
    }

    public void setArtworkFileName(String artworkFileName) {
        this.artworkFileName = artworkFileName;
    }

    public String getPureIpaFileName() {
        return pureIpaFileName;
    }

    public void setPureIpaFileName(String pureIpaFileName) {
        this.pureIpaFileName = pureIpaFileName;
    }

    public String getMetadataFileName() {
        return metadataFileName;
    }

    public void setMetadataFileName(String metadataFileName) {
        this.metadataFileName = metadataFileName;
    }

    public String getSinfLocalLocation() {
        return sinfLocalLocation;
    }

    public void setSinfLocalLocation(String sinfLocalLocation) {
        this.sinfLocalLocation = sinfLocalLocation;
    }

    public String getArtworkLocalLocation() {
        return artworkLocalLocation;
    }

    public void setArtworkLocalLocation(String artworkLocalLocation) {
        this.artworkLocalLocation = artworkLocalLocation;
    }

    public String getPureIpaLocalLocation() {
        return pureIpaLocalLocation;
    }

    public void setPureIpaLocalLocation(String pureIpaLocalLocation) {
        this.pureIpaLocalLocation = pureIpaLocalLocation;
    }

    public String getMetadataLocalLocation() {
        return metadataLocalLocation;
    }

    public void setMetadataLocalLocation(String metadataLocalLocation) {
        this.metadataLocalLocation = metadataLocalLocation;
    }

    public ApiRespWrapper<Boolean> getUploadPureIpaResp() {
        return uploadPureIpaResp;
    }

    public void setUploadPureIpaResp(ApiRespWrapper<Boolean> uploadPureIpaResp) {
        this.uploadPureIpaResp = uploadPureIpaResp;
    }

    public ApiRespWrapper<Boolean> getUploadSinfResp() {
        return uploadSinfResp;
    }

    public void setUploadSinfResp(ApiRespWrapper<Boolean> uploadSinfResp) {
        this.uploadSinfResp = uploadSinfResp;
    }

    public ApiRespWrapper<Boolean> getUploadMetadataResp() {
        return uploadMetadataResp;
    }

    public void setUploadMetadataResp(ApiRespWrapper<Boolean> uploadMetadataResp) {
        this.uploadMetadataResp = uploadMetadataResp;
    }

    public ApiRespWrapper<Boolean> getUploadArtworkResp() {
        return uploadArtworkResp;
    }

    public void setUploadArtworkResp(ApiRespWrapper<Boolean> uploadArtworkResp) {
        this.uploadArtworkResp = uploadArtworkResp;
    }

    public String getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(String processInfo) {
        this.processInfo = processInfo;
    }

    public long getDownloadTimeMillis() {
        if (downloadTimeMillis == 0) {
            if (downloadTaskStartTimeMillis > 0) {
                return System.currentTimeMillis() - downloadTaskStartTimeMillis;
            } else {
                return 0l;
            }
        }
        return downloadTimeMillis;
    }

    public void setDownloadTimeMillis(long downloadTimeMillis) {
        this.downloadTimeMillis = downloadTimeMillis;
    }

    public long getUploadTimeMillis() {
        if (uploadTimeMillis == 0) {
            if (uploadTaskStartTimeMillis > 0) {
                return System.currentTimeMillis() - uploadTaskStartTimeMillis;
            } else {
                return 0l;
            }
        }
        return uploadTimeMillis;
    }

    public void setUploadTimeMillis(long uploadTimeMillis) {
        this.uploadTimeMillis = uploadTimeMillis;
    }

    public long getDownloadTaskStartTimeMillis() {
        return downloadTaskStartTimeMillis;
    }

    public void setDownloadTaskStartTimeMillis(long downloadTaskStartTimeMillis) {
        this.downloadTaskStartTimeMillis = downloadTaskStartTimeMillis;
    }

    public long getUploadTaskStartTimeMillis() {
        return uploadTaskStartTimeMillis;
    }

    public void setUploadTaskStartTimeMillis(long uploadTaskStartTimeMillis) {
        this.uploadTaskStartTimeMillis = uploadTaskStartTimeMillis;
    }

    public String getIpaMd5() {
        return ipaMd5;
    }

    public void setIpaMd5(String ipaMd5) {
        this.ipaMd5 = ipaMd5;
    }

    @Override
    public String toString() {
        return "AppDownloadTask [appleId=" + appleId + ", passwd=" + passwd + ", proxy=" + proxy + ", taskId=" + taskId
                + ", downloadIpa=" + downloadIpa + ", appId=" + appId + ", country=" + country + ", sinfFileQiniuKey="
                + sinfFileQiniuKey + ", artworkFileQiniuKey=" + artworkFileQiniuKey + ", pureIpaFileQiniuKey="
                + pureIpaFileQiniuKey + ", metadataFileQiniuKey=" + metadataFileQiniuKey + ", sinfName=" + sinfName
                + ", artworkFileName=" + artworkFileName + ", pureIpaFileName=" + pureIpaFileName
                + ", metadataFileName=" + metadataFileName + ", processInfo=" + processInfo + ", downloadTimeMillis="
                + downloadTimeMillis + ", uploadTimeMillis=" + uploadTimeMillis + ", downloadTaskStartTimeMillis="
                + downloadTaskStartTimeMillis + ", uploadTaskStartTimeMillis=" + uploadTaskStartTimeMillis
                + ", sinfLocalLocation=" + sinfLocalLocation + ", artworkLocalLocation=" + artworkLocalLocation
                + ", pureIpaLocalLocation=" + pureIpaLocalLocation + ", metadataLocalLocation=" + metadataLocalLocation
                + ", ipaMd5=" + ipaMd5 + ", uploadPureIpaResp=" + uploadPureIpaResp + ", uploadSinfResp="
                + uploadSinfResp + ", uploadMetadataResp=" + uploadMetadataResp + ", uploadArtworkResp="
                + uploadArtworkResp + ", toString()=" + super.toString() + "]";
    }

}
