package org.yimon.admin.dal.repository;

import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.platform.dal.dao.StatementParameters;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.sqlbuilder.FreeSelectSqlBuilder;
import com.ctrip.platform.dal.dao.sqlbuilder.FreeUpdateSqlBuilder;
import org.reflections.Reflections;
import org.springframework.stereotype.Repository;
import org.yimon.admin.core.exception.RejectedException;
import org.yimon.admin.util.constant.ResultCode;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ym.gao
 * @description: 通用的crud仓库类
 * @date: 2024/4/29 17:16
 */
@Repository
public class CrudRepository {

    protected static final String DAL_ENTITY_PACKAGE = "org.yimon.admin.dal.entity";

    private final Map<String, DalQueryDao> DAL_QUERY_DAO_MAP = new HashMap<>();

    public CrudRepository() {
        //反射工具包，指明扫描路径
        Reflections reflections = new Reflections(DAL_ENTITY_PACKAGE);
        //获取带Handler注解的类
        Set<Class<?>> classList = reflections.getTypesAnnotatedWith(Database.class);
        classList.forEach(clazz -> {
            Database database = clazz.getAnnotation(Database.class);
            if (DAL_QUERY_DAO_MAP.get(database.name()) == null) {
                DAL_QUERY_DAO_MAP.put(database.name(), new DalQueryDao(database.name()));
            }
        });
    }

    /**
     * 增删改
     */
    public int operate(String dataBaseName, String sql, LinkedHashMap<String, Object> paramMap) throws SQLException {
        DalHints hints = DalHints.createIfAbsent(null);

        FreeUpdateSqlBuilder sqlBuilder = new FreeUpdateSqlBuilder();
        sqlBuilder.setTemplate(sql);
        StatementParameters parameters = this.covertParameters(paramMap);
        if (DAL_QUERY_DAO_MAP.get(dataBaseName) == null) {
            throw new RejectedException(ResultCode.DB_FAIL.code(), "database is not supported, please check");
        }
        return DAL_QUERY_DAO_MAP.get(dataBaseName).update(sqlBuilder, parameters, hints);
    }

    /**
     * pageNo/pageSize != null分页查询 or pageNo/pageSize == null查询指定条件内的所有
     **/
    public <T> List<T> selectList(String dataBaseName, String sql, LinkedHashMap<String, Object> paramMap, Integer pageNo, Integer pageSize, Class<T> tClass) throws SQLException {
        DalHints hints = DalHints.createIfAbsent(null);

        FreeSelectSqlBuilder<List<T>> builder = new FreeSelectSqlBuilder<>();
        builder.setTemplate(sql);
        builder.mapWith(tClass);
        if (pageNo != null && pageSize != null && pageNo > 0 && pageSize > 0) {
            builder.atPage(pageNo, pageSize);
        }
        StatementParameters parameters = this.covertParameters(paramMap);
        if (DAL_QUERY_DAO_MAP.get(dataBaseName) == null) {
            throw new RejectedException(ResultCode.DB_FAIL.code(), "database is not supported, please check");
        }
        return DAL_QUERY_DAO_MAP.get(dataBaseName).query(builder, parameters, hints);
    }

    /**
     * 查询第一条
     **/
    public <T> T selectFirst(String dataBaseName, String sql, LinkedHashMap<String, Object> paramMap, Class<T> tClass) throws SQLException {
        DalHints hints = DalHints.createIfAbsent(null);

        FreeSelectSqlBuilder<T> builder = new FreeSelectSqlBuilder<>();
        builder.setTemplate(sql);
        builder.mapWith(tClass).requireFirst().nullable();
        StatementParameters parameters = this.covertParameters(paramMap);
        if (DAL_QUERY_DAO_MAP.get(dataBaseName) == null) {
            throw new RejectedException(ResultCode.DB_FAIL.code(), "database is not supported, please check");
        }
        return DAL_QUERY_DAO_MAP.get(dataBaseName).query(builder, parameters, hints);
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
