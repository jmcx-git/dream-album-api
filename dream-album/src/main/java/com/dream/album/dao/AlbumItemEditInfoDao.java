package com.dream.album.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.dreambox.core.dto.album.AlbumItemEditInfo;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
public abstract class AlbumItemEditInfoDao extends AbsCommonDao<AlbumItemEditInfo> {

    public abstract AlbumItemEditInfo queryAlbumItemEditInfoByUk(AlbumItemEditInfo info) throws SQLException;
}
