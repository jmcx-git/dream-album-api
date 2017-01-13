// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;
import java.util.List;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.ActivityUserPrizeInfo;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
public abstract class ActivityUserPrizeInfoDao extends AbsCommonDao<ActivityUserPrizeInfo> {

    public abstract void updateBuild(List<ActivityUserPrizeInfo> datas) throws SQLException;

    public abstract int queryMinUnbuildId() throws SQLException;
}
