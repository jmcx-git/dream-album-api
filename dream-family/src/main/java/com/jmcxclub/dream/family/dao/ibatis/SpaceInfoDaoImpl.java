// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.SpaceInfoDao;
import com.jmcxclub.dream.family.dto.SpaceInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Repository("spaceInfoDao")
public class SpaceInfoDaoImpl extends SpaceInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public void updateNameAndBornDate(SpaceInfo g) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateSpaceInfoNameAndBornDate", g);
    }

    @Override
    public int insertReturnId(SpaceInfo g) throws SQLException {
        return SQLUtils.insertReturnId(getSqlMapClient(), "insertSpaceInfoReturnId", g);
    }

    @Override
    public void updateIcon(SpaceInfo g) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateSpaceInfoIcon", g);
    }

}
