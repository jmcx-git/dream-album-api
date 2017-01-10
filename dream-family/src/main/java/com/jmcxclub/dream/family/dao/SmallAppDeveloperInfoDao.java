// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.dreambox.core.dto.album.SmallAppDeveloperInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public abstract class SmallAppDeveloperInfoDao extends AbsCommonDao<SmallAppDeveloperInfo> {

    public abstract Integer queryIdByUk(String appId) throws SQLException;

}
