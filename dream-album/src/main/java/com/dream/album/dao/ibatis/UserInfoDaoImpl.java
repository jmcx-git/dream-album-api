package com.dream.album.dao.ibatis;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dream.album.dao.UserInfoDao;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("userInfoDao")
public class UserInfoDaoImpl extends UserInfoDao {

    @Resource(name = "dream-ablum-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

}
