package com.dream.album.dao;

import java.sql.SQLException;
import java.util.List;

import com.dreambox.core.dao.AbsCommonDao;
import com.dreambox.core.dto.album.UserAlbumCollectInfo;

public abstract class UserAlbumCollectInfoDao extends AbsCommonDao<UserAlbumCollectInfo> {

    public abstract List<Integer> getUserAlbumCollectInfoAlbumId(Integer userId) throws SQLException;

}
