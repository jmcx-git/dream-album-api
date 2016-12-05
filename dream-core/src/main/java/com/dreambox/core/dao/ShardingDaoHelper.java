// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dreambox.core.utils.SQLUtils;
import com.dreambox.web.utils.CollectionUtils;


/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public final class ShardingDaoHelper<G> {

    public static void createTable(String tableSuffix, AbsCommonShardingDao<?> dao) throws SQLException {
        SQLUtils.insertOrIgnore(dao.getSqlMapClient(), getCreateOrIgnoreSqlId(dao),
                SQLUtils.initTableSuffix(tableSuffix));
    }

    public static <G> void insertOrUpdate(String tableSuffix, G g, AbsCommonShardingDao<?> dao) throws SQLException {
        SQLUtils.insertOrUpdate(dao.getSqlMapClient(), getInsertOrUpdateSqlId(dao), tableSuffix, g);
    }

    public static <G> void insertOrUpdate(String tableSuffix, List<G> gg, AbsCommonShardingDao<?> dao)
            throws SQLException {
        SQLUtils.batchInsertOrUpdate(dao.getSqlMapClient(), getInsertOrUpdateSqlId(dao), tableSuffix, gg);
    }

    public static <G> boolean insertOrIgnore(String tableSuffix, G g, AbsCommonShardingDao<?> dao) throws SQLException {
        return SQLUtils.insertOrIgnore(dao.getSqlMapClient(), getInsertOrIgnoreSqlId(dao), tableSuffix, g);
    }

    public static <G> void updateStatus(String tableSuffix, G g, AbsCommonShardingDao<?> dao) throws SQLException {
        SQLUtils.update(dao.getSqlMapClient(), getUpdateStatusSqlId(dao), tableSuffix, g);
    }

    public static <G> void updateStatus(String tableSuffix, List<G> gg, AbsCommonShardingDao<?> dao)
            throws SQLException {
        SQLUtils.batchInsertOrUpdate(dao.getSqlMapClient(), getUpdateStatusSqlId(dao), tableSuffix, gg);
    }

    public static <G> void update(String tableSuffix, G g, AbsCommonShardingDao<?> dao) throws SQLException {
        SQLUtils.update(dao.getSqlMapClient(), getUpdateSqlId(dao), tableSuffix, g);
    }

    public static <G> List<G> queryList(String tableSuffix, G g, int start, int size, AbsCommonShardingDao<G> dao)
            throws SQLException {
        Map<String, Object> para = dao.buildListPara(tableSuffix, g);
        return SQLUtils.queryList(dao.getSqlMapClient(), getQueryListSqlId(dao), para, start, size);
    }

    public static <G> List<G> queryListByStartId(String tableSuffix, G g, int startId, int size,
            AbsCommonShardingDao<G> dao) throws SQLException {
        Map<String, Object> para = dao.buildListPara(tableSuffix, g);
        return SQLUtils.queryListByStartId(dao.getSqlMapClient(), getQueryListByStartIdFilterSqlId(dao), para, startId,
                size);
    }

    public static <G> List<G> queryListByEndId(String tableSuffix, G g, int endId, int size, AbsCommonShardingDao<G> dao)
            throws SQLException {
        Map<String, Object> para = dao.buildListPara(tableSuffix, g);
        return SQLUtils.queryListByEndId(dao.getSqlMapClient(), getQueryListByEndIdFilterSqlId(dao), para, endId, size);
    }

    public static <G> List<G> queryList(String tableSuffix, G g, String st, String et, int start, int size,
            AbsCommonShardingDao<G> dao) throws SQLException {
        Map<String, Object> para = dao.buildListPara(tableSuffix, g);
        SQLUtils.initStartEndTime(para, st, et);
        return SQLUtils.queryList(dao.getSqlMapClient(), getQueryListSqlId(dao), para, start, size);
    }

    public static <G> List<G> queryList(String tableSuffix, int startId, int size, AbsCommonShardingDao<G> dao)
            throws SQLException {
        return SQLUtils.queryList(dao.getSqlMapClient(), getQueryListByStartIdSqlId(dao),
                SQLUtils.buildShrdingStartIdSizeMap(tableSuffix, startId, size));
    }

    public static <G> G queryObject(String tableSuffix, int id, AbsCommonShardingDao<?> dao) throws SQLException {
        return SQLUtils.queryObject(dao.getSqlMapClient(), getQueryObjectSqlId(dao),
                SQLUtils.buildIdShardingMap(id, tableSuffix));
    }

    public static <G> List<G> queryList(String tableSuffix, List<Integer> ids, AbsCommonShardingDao<?> dao)
            throws SQLException {
        if (CollectionUtils.emptyOrNull(ids)) {
            return Collections.emptyList();
        }
        Map<String, Object> para = SQLUtils.buildIdsPara(tableSuffix, ids);
        return SQLUtils.queryList(dao.getSqlMapClient(), getQueryListByIdsSqlId(dao), para);
    }

    public static <G> long count(String tableSuffix, G g, AbsCommonShardingDao<G> dao) throws SQLException {
        Map<String, Object> para = dao.buildCountPara(tableSuffix, g);
        return SQLUtils.count(dao.getSqlMapClient(), getCountSqlId(dao), para);
    }

    public static <G> long count(String tableSuffix, G g, String st, String et, AbsCommonShardingDao<G> dao)
            throws SQLException {
        Map<String, Object> para = dao.buildCountPara(tableSuffix, g);
        SQLUtils.initStartEndTime(para, st, et);
        return SQLUtils.count(dao.getSqlMapClient(), getCountSqlId(dao), para);
    }

    protected static String getQueryListByStartIdSqlId(AbsCommonShardingDao<?> dao) {
        return "query" + dao.getName() + "ListByStartId";
    }

    protected static String getQueryListSqlId(AbsCommonShardingDao<?> dao) {
        return "query" + dao.getName() + "ListByFilter";
    }

    protected static String getQueryListByStartIdFilterSqlId(AbsCommonShardingDao<?> dao) {
        return "query" + dao.getName() + "ListByStartIdFilter";
    }

    protected static String getQueryListByEndIdFilterSqlId(AbsCommonShardingDao<?> dao) {
        return "query" + dao.getName() + "ListByEndIdFilter";
    }


    protected static String getQueryObjectSqlId(AbsCommonShardingDao<?> dao) {
        return "query" + dao.getName() + "ById";
    }

    protected static String getQueryListByIdsSqlId(AbsCommonShardingDao<?> dao) {
        return "query" + dao.getName() + "ByIds";
    }

    protected static String getCreateOrIgnoreSqlId(AbsCommonShardingDao<?> dao) {
        return "createOrIgnore" + dao.getName();
    }

    protected static String getInsertOrUpdateSqlId(AbsCommonShardingDao<?> dao) {
        return "insertOrUpdate" + dao.getName();
    }

    protected static String getInsertOrIgnoreSqlId(AbsCommonShardingDao<?> dao) {
        return "insertOrIgnore" + dao.getName();
    }

    protected static String getUpdateStatusSqlId(AbsCommonShardingDao<?> dao) {
        return "update" + dao.getName() + "Status";
    }

    protected static String getUpdateSqlId(AbsCommonShardingDao<?> dao) {
        return "update" + dao.getName();
    }

    protected static <G> String getCountSqlId(AbsCommonShardingDao<G> dao) {
        return "count" + dao.getName() + "ByFilter";
    }
}
