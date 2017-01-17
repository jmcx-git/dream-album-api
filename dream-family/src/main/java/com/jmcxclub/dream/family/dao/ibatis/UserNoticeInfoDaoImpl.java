// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.UserNoticeInfoDao;
import com.jmcxclub.dream.family.dto.UserNoticeInfo;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
@Repository("userNoticeInfoDaoImpl")
public class UserNoticeInfoDaoImpl extends UserNoticeInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public void updateRead(List<UserNoticeInfo> datas) throws SQLException {
        SQLUtils.batchInsertOrUpdate(getSqlMapClient(), "updateUserNoticeInfoRead", datas);
    }
}
