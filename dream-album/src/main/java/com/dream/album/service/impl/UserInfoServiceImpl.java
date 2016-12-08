package com.dream.album.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.UserInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.web.exception.ServiceException;

@Service("userInfoService")
public class UserInfoServiceImpl extends UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public CommonDao<UserInfo> getCommonDao() {
        return userInfoDao;
    }

    @Override
    public int addDataAndReturnId(UserInfo g) throws ServiceException {
        try {
            return userInfoDao.insertAndReturnId(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public UserInfo getDirectFromDbByOpenId(String openId) throws ServiceException {
        try {
            return userInfoDao.getUserInfoByOpenId(openId);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public void modifyUserInfo(UserInfo g) throws ServiceException {
        try {
            userInfoDao.update(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }


}
