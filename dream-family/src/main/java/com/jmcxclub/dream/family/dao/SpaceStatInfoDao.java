// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.SpaceStatInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public abstract class SpaceStatInfoDao extends AbsCommonDao<SpaceStatInfo> {

    public abstract void updateIncrViews(int id) throws SQLException;

    public abstract void updateIncrOccupants(int id) throws SQLException;

    public abstract void updateDecrOccupants(int id) throws SQLException;

    public abstract void updateIncrRecords(int id) throws SQLException;

    public abstract void updateDecrRecords(int id) throws SQLException;

}
