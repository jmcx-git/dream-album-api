package com.dreambox.core.dto.album;

import com.dreambox.core.dto.StatusSerializable;

/**
 * 相册小程序用户信息实体类
 * 
 * @author liuxinglong
 * @date 2016年12月6日
 */
public class AlbumUserInfo extends StatusSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8109466974743208176L;

    private int id;
    private String userId;
    private String openId;
    private String nickname;
    private String gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
    /**
     * 已创建的相册列表
     */
    private String createdAlbum;
    /**
     * 操作中的相册列表
     */
    private String handlerAlbum;
    /**
     * 已收藏的相册列表
     */
    private String collectAlbum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getCreatedAlbum() {
        return createdAlbum;
    }

    public void setCreatedAlbum(String createdAlbum) {
        this.createdAlbum = createdAlbum;
    }

    public String getHandlerAlbum() {
        return handlerAlbum;
    }

    public void setHandlerAlbum(String handlerAlbum) {
        this.handlerAlbum = handlerAlbum;
    }

    public String getCollectAlbum() {
        return collectAlbum;
    }

    public void setCollectAlbum(String collectAlbum) {
        this.collectAlbum = collectAlbum;
    }

    @Override
    public String toString() {
        return "AlbumUserInfo [id=" + id + ", userId=" + userId + ", openId=" + openId + ", nickname=" + nickname
                + ", gender=" + gender + ", city=" + city + ", province=" + province + ", country=" + country
                + ", avatarUrl=" + avatarUrl + ", unionId=" + unionId + ", createdAlbum=" + createdAlbum
                + ", handlerAlbum=" + handlerAlbum + ", collectAlbum=" + collectAlbum + "]";
    }
}
