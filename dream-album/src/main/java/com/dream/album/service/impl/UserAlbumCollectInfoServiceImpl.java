package com.dream.album.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.UserAlbumCollectInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.UserAlbumCollectInfo;
import com.dreambox.core.service.album.UserAlbumCollectInfoService;
import com.dreambox.web.exception.ServiceException;

@Service("userAlbumCollectInfo")
public class UserAlbumCollectInfoServiceImpl extends UserAlbumCollectInfoService {

    @Autowired
    private UserAlbumCollectInfoDao userAlbumCollectInfoDao;

    @Override
    public CommonDao<UserAlbumCollectInfo> getCommonDao() {
        return userAlbumCollectInfoDao;
    }

    @Override
    public List<Integer> getCollectAlbumInfoId(Integer userId) throws ServiceException {
        try {
            return userAlbumCollectInfoDao.getUserAlbumCollectInfoAlbumId(userId);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

}
