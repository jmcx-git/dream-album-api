// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.UserReadNoticeRecordDao;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
public class UserReadNoticeRecordDaoImpl extends UserReadNoticeRecordDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public void updateReadTime(int id) throws SQLException {
        SQLUtils.update(getSqlMapClient(), "updateUserReadNoticeRecordReadTime", id);
    }

}
