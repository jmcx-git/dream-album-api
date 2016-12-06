// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.dao.ibatis;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.AlbumInfoDao;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
@Repository("albumInfoDao")
public class AlbumInfoDaoImplAlbumInfoDao extends AlbumInfoDao {
    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

}
