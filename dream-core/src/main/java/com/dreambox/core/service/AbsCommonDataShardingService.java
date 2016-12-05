// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dreambox.core.dao.CommonShardingDao;
import com.dreambox.web.exception.ServiceException;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public abstract class AbsCommonDataShardingService<G> implements CommonDataShardingService<G> {
    public abstract CommonShardingDao<G> getCommonDao();

    @Override
    public List<G> listDirectFromDb(String tableSuffix, G g) throws ServiceException {
        return listDirectFromDb(tableSuffix, g, 0, Integer.MAX_VALUE);
    }

    @Override
    public void createTable(String tableSuffix) throws ServiceException {
        try {
            getCommonDao().createTable(tableSuffix);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public List<G> listDirectFromDb(String tableSuffix, G g, int start, int size) throws ServiceException {
        try {
            return getCommonDao().queryList(tableSuffix, g, start, size);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public List<G> listByStartIdDirectFromDb(String tableSuffix, G g, int startId, int size) throws ServiceException {
        try {
            return getCommonDao().queryListByStartId(tableSuffix, g, startId, size);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public List<G> listByEndIdDirectFromDb(String tableSuffix, G g, int endId, int size) throws ServiceException {
        try {
            return getCommonDao().queryListByEndId(tableSuffix, g, endId, size);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public List<G> listDirectFromDb(String tableSuffix, G g, String st, String et, int start, int size)
            throws ServiceException {
        if (StringUtils.isEmpty(st) && StringUtils.isEmpty(et)) {
            return listDirectFromDb(tableSuffix, g, start, size);
        }
        try {
            return getCommonDao().queryList(tableSuffix, g, st, et, start, size);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public G getDirectFromDb(String tableSuffix, int id) throws ServiceException {
        try {
            return getCommonDao().queryObject(tableSuffix, id);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public List<G> getDirectFromDb(String tableSuffix, List<Integer> ids) throws ServiceException {
        try {
            return getCommonDao().queryList(tableSuffix, ids);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }


    @Override
    public void addData(String tableSuffix, G g) throws ServiceException {
        g = beforToDb(g);
        try {
            getCommonDao().insertOrUpdate(tableSuffix, g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        afterAddData(g);
    }

    @Override
    public boolean addOrIgnoreData(String tableSuffix, G g) throws ServiceException {
        boolean ret = false;
        g = beforToDb(g);
        try {
            ret = getCommonDao().insertOrIgnore(tableSuffix, g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        if (ret) {
            afterAddData(g);
        }
        return ret;
    }

    protected void afterAddData(G g) {
        List<G> gg = new ArrayList<G>();
        gg.add(g);
        afterAddData(gg);
    }

    protected void afterAddData(List<G> gg) {
        afterModifyData(gg);
    }

    protected void afterModifyData(G g) {
        if (g == null) {
            throw ServiceException.getInternalException("The modify object is null");
        }
        List<G> gg = new ArrayList<G>();
        gg.add(g);
        afterModifyData(gg);
    }

    protected void beforeModifyData(G g) {
        if (g == null) {
            throw ServiceException.getInternalException("The modify object is null");
        }
        List<G> gg = new ArrayList<G>();
        gg.add(g);
        beforeModifyData(gg);
    }

    protected void beforeModifyData(List<G> gg) {
    }

    protected void afterModifyData(List<G> gg) {
    }

    protected void afterModifyStatus(List<G> gg) {
    }

    protected G beforToDb(G g) {
        return g;
    }

    protected List<G> beforToDb(List<G> gg) {
        for (G g : gg) {
            beforToDb(g);
        }
        return gg;
    }

    @Override
    public void modifyStatus(String tableSuffix, G g) throws ServiceException {
        if (g == null) {
            return;
        }
        List<G> gg = new ArrayList<G>();
        gg.add(g);
        modiftStatus(tableSuffix, gg);
    }

    @Override
    public void modiftStatus(String tableSuffix, List<G> gg) throws ServiceException {
        beforeModifyData(gg);
        try {
            getCommonDao().updateStatus(tableSuffix, gg);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        afterModifyStatus(gg);
    }

    @Override
    public long countDirectFromDb(String tableSuffix, G g) throws ServiceException {
        try {
            return getCommonDao().count(tableSuffix, g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public long countDirectFromDb(String tableSuffix, G g, String st, String et) throws ServiceException {
        if (StringUtils.isEmpty(st) && StringUtils.isEmpty(et)) {
            return countDirectFromDb(tableSuffix, g);
        }
        try {
            return getCommonDao().count(tableSuffix, g, st, et);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public void batchAdd(String tableSuffix, List<G> gg) throws ServiceException {
        gg = beforToDb(gg);
        try {
            getCommonDao().insertOrUpdate(tableSuffix, gg);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        afterAddData(gg);
    }

}
