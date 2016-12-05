package com.dreambox.core.dao;

import java.sql.SQLException;
import java.util.List;

public interface ShardingLoadDao<T> {

    List<T> queryList(String tableSuffix, int startId, int size) throws SQLException;

}
