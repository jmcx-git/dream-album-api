package com.dream.album.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.UserAlbumInfoDao;
import com.dreambox.core.dto.album.UserAlbumInfo;
import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Repository("userAlbumInfoDao")
public class UserAlbumInfoDaoImpl extends UserAlbumInfoDao {
    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public void updateUserAlbumInfoStep(UserAlbumInfo info) throws SQLException {
        SQLUtils.update(sqlMapClient, "updateUserAlbumInfoStep", info);
    }

}
