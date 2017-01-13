// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.ActivityVoteDetailInfoDao;
import com.jmcxclub.dream.family.dto.ActivityVoteDetailInfo;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
@Repository("activityVoteDetailInfoDao")
public class ActivityVoteDetailInfoDaoImpl extends ActivityVoteDetailInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public Integer queryIdByUk(ActivityVoteDetailInfo t) throws SQLException {
        return SQLUtils.queryObject(getSqlMapClient(), "queryActivityVoteDetailInfoIdByUk", t);
    }
}
