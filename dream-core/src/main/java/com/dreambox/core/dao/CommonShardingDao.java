// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public interface CommonShardingDao<G> extends ShardingLoadDao<G> {
    public void createTable(String tableSuffix) throws SQLException;

    public void insertOrUpdate(String tableSuffix, G g) throws SQLException;

    public void insertOrUpdate(String tableSuffix, List<G> gg) throws SQLException;

    public boolean insertOrIgnore(String tableSuffix, G g) throws SQLException;

    public void updateStatus(String tableSuffix, G g) throws SQLException;

    public void updateStatus(String tableSuffix, List<G> gg) throws SQLException;

    public void update(String tableSuffix, G g) throws SQLException;

    public long count(String tableSuffix, G g) throws SQLException;

    public long count(String tableSuffix, G g, String st, String et) throws SQLException;

    public List<G> queryList(String tableSuffix, G g, int start, int size) throws SQLException;

    public List<G> queryListByStartId(String tableSuffix, G g, int startId, int size) throws SQLException;

    public List<G> queryListByEndId(String tableSuffix, G g, int endId, int size) throws SQLException;

    public List<G> queryList(String tableSuffix, G g, String st, String et, int start, int size) throws SQLException;

    public List<G> queryList(String tableSuffix, List<Integer> ids) throws SQLException;

    public G queryObject(String tableSuffix, int id) throws SQLException;

    public SqlMapClient getSqlMapClient();

    public Map<String, Object> buildListPara(String tableSuffix, G g);

    public Map<String, Object> buildCountPara(String tableSuffix, G g);
}
