// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.UserSpaceInteractionInfoDao;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Repository("userSpaceInteractionInfoDao")
public class UserSpaceInteractionInfoDaoImpl extends UserSpaceInteractionInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public Integer queryIdByUk(UserSpaceInteractionInfo t) throws SQLException {
        return SQLUtils.queryObject(getSqlMapClient(), "queryUserSpaceInteractionInfoIdByUk", t);
    }

    @Override
    public void updateIncrViews(UserSpaceInteractionInfo t) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateIncrUserSpaceInteractionInfoViews", t);
    }

    @Override
    public void updateIncrLikes(UserSpaceInteractionInfo t) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateIncrUserSpaceInteractionInfoLikes", t);
    }

    @Override
    public void updateDecrLikes(UserSpaceInteractionInfo t) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateDecrUserSpaceInteractionInfoLikes", t);
    }

    @Override
    public void updateIncrComments(UserSpaceInteractionInfo t) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateIncrUserSpaceInteractionInfoComments", t);
    }

    @Override
    public void updateDecrComments(UserSpaceInteractionInfo t) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateDecrUserSpaceInteractionInfoComments", t);
    }

    @Override
    public void updateStatusByUk(UserSpaceInteractionInfo t) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateUserSpaceInteractionInfoStatusByUk", t);
    }

}
