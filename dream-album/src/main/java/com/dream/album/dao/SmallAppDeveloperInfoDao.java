// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.dreambox.core.dto.album.SmallAppDeveloperInfo;

/**
 * @author mokous86@gmail.com create date: 2016年12月27日
 *
 */
public abstract class SmallAppDeveloperInfoDao extends AbsCommonDao<SmallAppDeveloperInfo> {

    public abstract Integer queryIdByUk(String appId) throws SQLException;
}
