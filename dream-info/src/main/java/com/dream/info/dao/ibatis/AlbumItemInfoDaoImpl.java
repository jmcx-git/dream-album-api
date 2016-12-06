package com.dream.info.dao.ibatis;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.info.dao.AlbumItemInfoDao;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Repository("albumItemInfoDao")
public abstract class AlbumItemInfoDaoImpl extends AlbumItemInfoDao {
    @Resource(name = "ios-info-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
}
