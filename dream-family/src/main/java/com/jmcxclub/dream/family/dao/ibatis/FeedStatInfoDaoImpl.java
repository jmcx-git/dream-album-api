// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.FeedStatInfoDao;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Repository("feedStatInfoDao")
public class FeedStatInfoDaoImpl extends FeedStatInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public void updateDecrComments(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateDecrFeedStatInfoComments", id);
    }

    @Override
    public void updateIncrComments(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateIncrFeedStatInfoComments", id);
    }

    @Override
    public void updateDecrLikes(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateDecrFeedStatInfoLikes", id);
    }

    @Override
    public void updateIncrLikes(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateIncrFeedStatInfoLikes", id);
    }

}
