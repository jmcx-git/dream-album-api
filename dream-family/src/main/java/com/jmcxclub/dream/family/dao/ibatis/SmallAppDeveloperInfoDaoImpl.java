// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.SmallAppDeveloperInfoDao;

/**
 * @author mokous86@gmail.com
 * create date: Jan 10, 2017
 *
 
 */
@Repository("smallAppDeveloperInfoDao")
public class SmallAppDeveloperInfoDaoImpl extends SmallAppDeveloperInfoDao {
    @Resource(name = "dream-family-sql-client")
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
