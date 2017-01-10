// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.SpaceStatInfoDao;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Repository("spaceStatInfoDao")
public class SpaceStatInfoDaoImpl extends SpaceStatInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public void updateIncrViews(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateIncrSpaceStatInfoViews", id);
    }

    @Override
    public void updateIncrOccupants(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateIncrSpaceStatInfoOccupants", id);
    }

    @Override
    public void updateDecrOccupants(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateDecrSpaceStatInfoOccupants", id);
    }

    @Override
    public void updateIncrRecords(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateIncrSpaceStatInfoRecords", id);
    }

    @Override
    public void updateDecrRecords(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateDecrSpaceStatInfoRecords", id);
    }

}
