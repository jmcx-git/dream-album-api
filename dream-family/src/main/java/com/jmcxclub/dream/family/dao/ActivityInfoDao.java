// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.ActivityInfo;

/**
 * @author mokous86@gmail.com
 * create date: Jan 11, 2017
 *
 
 */
public abstract class ActivityInfoDao extends AbsCommonDao<ActivityInfo> {

    public abstract void updateFinish(ActivityInfo g)throws SQLException;

}
