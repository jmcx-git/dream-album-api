// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.dao;

import java.sql.SQLException;

import com.dreambox.core.dao.AbsCommonDao;
import com.jmcxclub.dream.family.dto.UserReadNoticeRecord;

/**
 * @author mokous86@gmail.com create date: Jan 13, 2017
 *
 */
public abstract class UserReadNoticeRecordDao extends AbsCommonDao<UserReadNoticeRecord> {
    public abstract void updateReadTime(int id) throws SQLException;
}
