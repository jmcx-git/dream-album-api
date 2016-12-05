// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.util.List;

import com.dreambox.web.exception.ServiceException;

/**
 * 处理数据存储在分库分表的基础服务
 * 
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public interface CommonDataShardingService<G> {
    public abstract void createTable(String tableSuffix) throws ServiceException;

    public List<G> listDirectFromDb(String tableSuffix, G g) throws ServiceException;

    public List<G> listDirectFromDb(String tableSuffix, G g, String st, String et, int start, int size)
            throws ServiceException;

    public List<G> listDirectFromDb(String tableSuffix, G g, int start, int size) throws ServiceException;

    public List<G> listByStartIdDirectFromDb(String tableSuffix, G g, int startId, int size) throws ServiceException;

    public List<G> listByEndIdDirectFromDb(String tableSuffix, G g, int endId, int size) throws ServiceException;

    public long countDirectFromDb(String tableSuffix, G g) throws ServiceException;

    public long countDirectFromDb(String tableSuffix, G g, String st, String et) throws ServiceException;

    public G getDirectFromDb(String tableSuffix, int id) throws ServiceException;

    public List<G> getDirectFromDb(String tableSuffix, List<Integer> ids) throws ServiceException;

    public void addData(String tableSuffix, G g) throws ServiceException;

    public void batchAdd(String tableSuffix, List<G> gg) throws ServiceException;

    public boolean addOrIgnoreData(String tableSuffix, G g) throws ServiceException;

    public void modifyStatus(String tableSuffix, G g) throws ServiceException;

    public void modiftStatus(String tableSuffix, List<G> gg) throws ServiceException;

}
