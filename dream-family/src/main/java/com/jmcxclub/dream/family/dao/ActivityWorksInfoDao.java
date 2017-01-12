// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.ActivityWorksInfo;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public abstract class ActivityWorksInfoDao extends AbsCommonDao<ActivityWorksInfo> {

    public abstract Integer queryIdByUk(ActivityWorksInfo t) throws SQLException;

    public abstract int insertReturnId(ActivityWorksInfo g) throws SQLException;
}
