package com.dream.album.dao.ibatis;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.AlbumItemInfoDao;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Repository("albumItemInfoDao")
public class AlbumItemInfoDaoImpl extends AlbumItemInfoDao {
    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
}
