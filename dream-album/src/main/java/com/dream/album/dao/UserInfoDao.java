package com.dream.album.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.dreambox.core.dto.album.UserInfo;

public abstract class UserInfoDao extends AbsCommonDao<UserInfo> {

    public abstract int insertAndReturnId(UserInfo g) throws SQLException;

    public abstract UserInfo getUserInfoByOpenId(String openId) throws SQLException ;

}
