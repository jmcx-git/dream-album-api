package com.dream.info.dao.ibatis;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.info.dao.UserAlbumItemInfoDao;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Repository("userAlbumItemInfoDao")
public class UserAlbumItemInfoDaoImpl extends UserAlbumItemInfoDao {
    @Resource(name = "dream-info-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
}
