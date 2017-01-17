// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.UserSpaceInteractionInfo;

/**
 * @author mokous86@gmail.com create date: Jan 11, 2017
 *
 */
public abstract class UserSpaceInteractionInfoDao extends AbsCommonDao<UserSpaceInteractionInfo> {

    public abstract Integer queryIdByUk(UserSpaceInteractionInfo t) throws SQLException;

    public abstract void updateIncrViews(UserSpaceInteractionInfo t) throws SQLException;

    public abstract void updateIncrLikes(UserSpaceInteractionInfo t) throws SQLException;

    public abstract void updateDecrLikes(UserSpaceInteractionInfo t) throws SQLException;

    public abstract void updateIncrComments(UserSpaceInteractionInfo t) throws SQLException;

    public abstract void updateDecrComments(UserSpaceInteractionInfo t) throws SQLException;

}
