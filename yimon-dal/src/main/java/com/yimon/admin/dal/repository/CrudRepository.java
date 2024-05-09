package com.yimon.admin.dal.repository;

import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.platform.dal.dao.StatementParameters;
import com.ctrip.platform.dal.dao.sqlbuilder.FreeSelectSqlBuilder;
import com.ctrip.platform.dal.dao.sqlbuilder.FreeUpdateSqlBuilder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ym.gao
 * @description: 通用的crud仓库类
 * @date: 2024/4/29 17:16
 */
@Repository
public class CrudRepository {

    private static final String DATA_BASE = "MySql_yimon";
    private final DalQueryDao queryDao;

    public CrudRepository() {
        this.queryDao = new DalQueryDao(DATA_BASE);
    }

    /**
     * 增删改
     */
    public int operate(String sql, LinkedHashMap<String, Object> paramMap) throws SQLException {
        DalHints hints = DalHints.createIfAbsent(null);
        FreeUpdateSqlBuilder sqlBuilder = new FreeUpdateSqlBuilder();
        sqlBuilder.setTemplate(sql);
        StatementParameters parameters = covertParameters(paramMap);
        return queryDao.update(sqlBuilder, parameters, hints);
    }

    /**
     * pageNo/pageSize != null分页查询 or pageNo/pageSize == null查询指定条件内的所有
     **/
    public <T> List<T> selectList(String sql, LinkedHashMap<String, Object> paramMap, Integer pageNo, Integer pageSize, Class<T> tClass) throws SQLException {
        DalHints hints = DalHints.createIfAbsent(null);

        FreeSelectSqlBuilder<List<T>> builder = new FreeSelectSqlBuilder<>();
        builder.setTemplate(sql);
        builder.mapWith(tClass);
        if (pageNo != null && pageSize != null) {
            builder.atPage(pageNo, pageSize);
        }
        StatementParameters parameters = covertParameters(paramMap);
        return queryDao.query(builder, parameters, hints);
    }

    /**
     * 查询第一条
     **/
    public <T> T selectFirst(String sql, LinkedHashMap<String, Object> paramMap, Class<T> tClass) throws SQLException {
        DalHints hints = DalHints.createIfAbsent(null);


        FreeSelectSqlBuilder<T> builder = new FreeSelectSqlBuilder<>();
        builder.setTemplate(sql);
        builder.mapWith(tClass).requireFirst().nullable();
        StatementParameters parameters = covertParameters(paramMap);
        return queryDao.query(builder, parameters, hints);
    }

    private StatementParameters covertParameters(Map<String, Object> paramMap) {
        StatementParameters parameters = new StatementParameters();
        AtomicInteger i = new AtomicInteger(1);
        paramMap.forEach((k, v) -> {
            if (v instanceof List) {
                parameters.setSensitiveInParameter(i.getAndIncrement(), k, (List<?>) v);
            } else {
                parameters.setSensitive(i.getAndIncrement(), k, v);
            }
        });
        return parameters;
    }
}
