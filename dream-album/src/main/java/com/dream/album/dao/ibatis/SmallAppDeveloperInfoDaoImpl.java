// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.SmallAppDeveloperInfoDao;
import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author mokous86@gmail.com create date: Dec 27, 2016
 *
 */
@Repository("smallAppDeveloperInfoDao")
public class SmallAppDeveloperInfoDaoImpl extends SmallAppDeveloperInfoDao {

    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public Integer queryIdByUk(String appId) throws SQLException {
        return SQLUtils.queryObject(sqlMapClient, "querySmallAppDeveloperInfoIdByUk", appId);
    }

}
