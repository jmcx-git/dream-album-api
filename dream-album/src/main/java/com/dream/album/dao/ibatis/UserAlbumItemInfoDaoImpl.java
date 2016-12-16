package com.dream.album.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.UserAlbumItemInfoDao;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Repository("userAlbumItemInfoDao")
public class UserAlbumItemInfoDaoImpl extends UserAlbumItemInfoDao {
    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public UserAlbumItemInfo queryUserAlbumItemInfoByUk(UserAlbumItemInfo info) throws SQLException {
        return SQLUtils.queryObject(sqlMapClient, "queryUserAlbumItemInfoByUk", info);
    }
}
