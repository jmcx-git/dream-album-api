// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.FeedInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public abstract class FeedInfoDao extends AbsCommonDao<FeedInfo> {

    public abstract int insertReturnId(FeedInfo g) throws SQLException;
}
