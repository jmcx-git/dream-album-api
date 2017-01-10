// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.SpaceInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public abstract class SpaceInfoDao extends AbsCommonDao<SpaceInfo> {

    public abstract void updateTitle(SpaceInfo g) throws SQLException;

    public abstract int insertReturnId(SpaceInfo g) throws SQLException;

}
