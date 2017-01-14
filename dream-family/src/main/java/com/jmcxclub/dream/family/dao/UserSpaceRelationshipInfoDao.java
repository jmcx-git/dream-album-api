// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.UserSpaceRelationshipInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public abstract class UserSpaceRelationshipInfoDao extends AbsCommonDao<UserSpaceRelationshipInfo> {

    public abstract Integer queryIdByUk(UserSpaceRelationshipInfo g) throws SQLException;
}
