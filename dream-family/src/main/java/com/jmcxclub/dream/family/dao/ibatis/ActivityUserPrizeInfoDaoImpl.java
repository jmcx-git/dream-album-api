// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.ActivityUserPrizeInfoDao;
import com.jmcxclub.dream.family.dto.ActivityUserPrizeInfo;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
@Repository("activityUserPrizeInfoDao")
public class ActivityUserPrizeInfoDaoImpl extends ActivityUserPrizeInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public void updateBuild(List<ActivityUserPrizeInfo> datas) throws SQLException {
        SQLUtils.batchInsertOrUpdate(getSqlMapClient(), "updateActivityUserPrizeInfoBuild", datas);
    }

    @Override
    public int queryMinUnbuildId() throws SQLException {
        return SQLUtils.queryId(getSqlMapClient(), "queryActivityUserPrizeInfoMinUnbuildId", null, -1);
    }

}
