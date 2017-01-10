// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.FeedStatInfo;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public abstract class FeedStatInfoDao extends AbsCommonDao<FeedStatInfo> {

    public abstract void updateDecrComments(int id) throws SQLException;

    public abstract void updateIncrComments(int id) throws SQLException;

    public abstract void updateDecrLikes(int id) throws SQLException;

    public abstract void updateIncrLikes(int id) throws SQLException;

}
