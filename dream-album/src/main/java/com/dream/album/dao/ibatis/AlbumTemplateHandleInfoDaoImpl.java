package com.dream.album.dao.ibatis;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.AlbumTemplateHandleInfoDao;
import com.ibatis.sqlmap.client.SqlMapClient;

/** 
 * @author  liuxinglong
 * @date     2016年12月6日  
 */
@Repository("albumTemplateHandleInfoDao")
public abstract class AlbumTemplateHandleInfoDaoImpl extends AlbumTemplateHandleInfoDao {
    @Resource(name = "ios-dream-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
}
