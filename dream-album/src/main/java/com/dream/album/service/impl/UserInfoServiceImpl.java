package com.dream.album.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.dao.UserInfoDao;
import com.dreambox.core.dao.CommonDao;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl extends UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public CommonDao<UserInfo> getCommonDao() {
        return userInfoDao;
    }


}
