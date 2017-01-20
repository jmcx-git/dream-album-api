// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.FeedInfoDao;
import com.jmcxclub.dream.family.dto.FeedInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
@Repository("feedInfoDao")
public class FeedInfoDaoImpl extends FeedInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public int insertReturnId(FeedInfo g) throws SQLException {
        return SQLUtils.insertReturnId(getSqlMapClient(), "insertFeedInfoReturnId", g);
    }

    @Override
    public boolean updateIllustration(FeedInfo feedInfo) throws SQLException {
        return SQLUtils.update(getSqlMapClient(), "updateFeedInfoIllustration", feedInfo) > 0;
    }

    @Override
    public String queryIllustration(int id) throws SQLException {
        return SQLUtils.queryObject(getSqlMapClient(), "queryFeedInfoIllustration", id);
    }

}
