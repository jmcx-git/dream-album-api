package com.dreambox.core.dto.album;

import com.dreambox.core.DbStatus;

/**
 * 
 * @author yhx
 *
 */
public class UserAlbumCollectInfo extends DbStatus {

    /**
     * 
     */
    private static final long serialVersionUID = -987102919559938374L;

    private int id;
    private int albumId;// 模版id
    private int userId;// 用户id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
