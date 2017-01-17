// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.SpaceSecertInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public abstract class SpaceSecertInfoDao extends AbsCommonDao<SpaceSecertInfo> {

    public abstract Integer queryIdByUk(SpaceSecertInfo t) throws SQLException;

    public abstract void updateStatusBySpaceId(int spaceId) throws SQLException;

    public abstract String queryLastSpaceSecert(int spaceId) throws SQLException;

}
