package com.dream.album.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.dreambox.core.dto.album.UserAlbumInfo;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
public abstract class UserAlbumInfoDao extends AbsCommonDao<UserAlbumInfo> {

    public abstract void updateUserAlbumInfoStep(UserAlbumInfo info) throws SQLException;

    public abstract UserAlbumInfo queryUserAlbumInfoByUk(UserAlbumInfo info) throws SQLException;

    public abstract void updateUserAlbumInfoCompleteAndPreImg(UserAlbumInfo info) throws SQLException;

    public abstract UserAlbumInfo queryLatestByUserAlbumAndComplete(UserAlbumInfo info) throws SQLException;

    public abstract boolean updateUserAlbumInfoTitle(UserAlbumInfo info) throws SQLException;

    public abstract void updateUserAlbumInfoCoverImg(UserAlbumInfo info) throws SQLException;
}
