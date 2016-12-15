package com.dream.album.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.dreambox.core.dto.album.UserAlbumItemInfo;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
public abstract class UserAlbumItemInfoDao extends AbsCommonDao<UserAlbumItemInfo> {

    public abstract UserAlbumItemInfo queryUserAlbumItemInfoByUk(UserAlbumItemInfo info) throws SQLException;
}
