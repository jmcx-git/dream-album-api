// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.SpaceSecertInfoDao;
import com.jmcxclub.dream.family.dto.SpaceSecertInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Repository("spaceSecertInfoDao")
public class SpaceSecertInfoDaoImpl extends SpaceSecertInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public Integer queryIdByUk(SpaceSecertInfo t) throws SQLException {
        return SQLUtils.queryObject(getSqlMapClient(), "querySpaceSecertInfoIdByUk", t);
    }

    @Override
    public void updateStatusBySpaceId(int spaceId) throws SQLException {
        SpaceSecertInfo g = new SpaceSecertInfo();
        g.setStatus(SpaceSecertInfo.STATUS_DEL);
        g.setSpaceId(spaceId);
        SQLUtils.update(getSqlMapClient(), "updateSpaceSecertInfoStatusBySpaceId", g);
    }

    @Override
    public String queryLastSpaceSecert(int spaceId) throws SQLException {
        return SQLUtils.queryObject(getSqlMapClient(), "querySpaceSecertInfoLastSpaceSecert", spaceId);
    }
}
