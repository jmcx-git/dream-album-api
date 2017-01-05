// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.AlbumCoverItemInfoDao;
import com.dreambox.core.dto.album.AlbumCoverItemInfo;
import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author mokous86@gmail.com create date: Jan 5, 2017
 *
 */
@Repository("albumCoverItemInfoDao")
public class AlbumCoverItemInfoDaoImpl extends AlbumCoverItemInfoDao {
    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public Integer queryIdByUk(AlbumCoverItemInfo t) throws SQLException {
        return SQLUtils.queryObject(sqlMapClient, "queryAlbumCoverItemInfoIdByUk", t.getAlbumId());
    }


}
