// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao.ibatis;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.jmcxclub.dream.family.dao.SystemNoticeInfoDao;

/**
 * @author mokous86@gmail.com
 * create date: Jan 13, 2017
 *
 
 */
@Repository("systemNoticeInfoDao")
public class SystemNoticeInfoDaoImpl extends SystemNoticeInfoDao {
    @Resource(name = "dream-family-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
}
