package com.dream.album.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.AlbumItemEditInfoDao;
import com.dreambox.core.dto.album.AlbumItemEditInfo;
import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Repository("albumItemEditInfoDao")
public class AlbumItemEditInfoDaoImpl extends AlbumItemEditInfoDao {
    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public AlbumItemEditInfo queryAlbumItemEditInfoByUk(AlbumItemEditInfo info) throws SQLException {
        return SQLUtils.queryObject(sqlMapClient, "queryAlbumItemEditInfoByUk", info);
    }

}
