package com.dream.album.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dream.album.dao.UserAlbumCollectInfoDao;
import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;

@Service("userAlbumCollectInfoDao")
public class UserAlbumCollectInfoDaoImpl extends UserAlbumCollectInfoDao {

    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public List<Integer> getUserAlbumCollectInfoAlbumId(Integer userId) throws SQLException {
        return SQLUtils.queryList(sqlMapClient, "getUserAlbumCollectInfoAlbumId", userId);
    }

}
