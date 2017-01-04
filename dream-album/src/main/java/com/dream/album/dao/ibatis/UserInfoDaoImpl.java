package com.dream.album.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.UserInfoDao;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.utils.SQLUtils;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("userInfoDao")
public class UserInfoDaoImpl extends UserInfoDao {

    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public int insertAndReturnId(UserInfo g) throws SQLException {
        return SQLUtils.insertReturnId(sqlMapClient, "insertUserInfoReturnId", g);
    }

    @Override
    public UserInfo getUserInfoByOpenId(String openId) throws SQLException {
        return SQLUtils.queryObject(sqlMapClient, "getUserInfoByOpenId", openId);
    }

    @Override
    public Integer queryIdByUk(String openId) throws SQLException {
        return SQLUtils.queryObject(sqlMapClient, "queryUserInfoIdByUk", openId);
    }

}
