// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.dreambox.core.dto.album.AlbumCoverItemInfo;

/**
 * @author mokous86@gmail.com create date: Jan 5, 2017
 *
 */
public abstract class AlbumCoverItemInfoDao extends AbsCommonDao<AlbumCoverItemInfo> {

    public abstract Integer queryIdByUk(AlbumCoverItemInfo t) throws SQLException;

}
