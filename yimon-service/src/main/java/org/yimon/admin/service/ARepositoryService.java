package org.yimon.admin.service;

import com.ctrip.platform.dal.dao.annotation.Database;
import org.reflections.Reflections;
import org.yimon.admin.core.exception.ValidateException;
import org.yimon.admin.core.util.StringUtils;
import org.yimon.admin.util.constant.ResultCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: ym.gao
 * @description: crud抽象类，主要是提取公共方法
 * @date: 2024/5/8 15:44
 */
public abstract class ARepositoryService {

    protected static final String DAL_ENTITY_PACKAGE = "org.yimon.admin.dal.entity";

    protected static final String WHERE = "@WHERE@";

    protected static final String COLUMN = "@COLUMN@";

    protected static final String ORDER = "@ORDER@";

    protected static final String PAGE_NO = "@PAGE_NO@";

    protected static final String PAGE_SIZE = "@PAGE_SIZE@";

    protected static final String PAGE = "@PAGE@";

    protected static final String TOTAL = "@TOTAL@";

    protected static final String ROWS = "@ROWS@";

    protected static final String RESULT = "@RESULT@";

    /**
     * 获取指定表的对象类
     *
     * @param tableName 表
     * @return Class
     */
    protected Class<?> getEntityClass(String tableName) {
        //反射工具包，指明扫描路径
        Reflections reflections = new Reflections(DAL_ENTITY_PACKAGE);
        //获取带Handler注解的类
        Set<Class<?>> classList = reflections.getTypesAnnotatedWith(Table.class);
        return classList.stream().filter(clazz -> {
            Table table = clazz.getAnnotation(Table.class);
            return tableName.equals(table.name());
        }).findFirst().orElseThrow(() -> new ValidateException(ResultCode.PARAMS_ERROR.code(), StringUtils.join("Table '", tableName, "' doesn't exist, please check")));
    }

    /**
     * 获取指定表所在的数据库
     *
     * @param tableName 表
     * @return String
     */
    protected String getDataBaseName(String tableName) {
        Class<?> tableClass = this.getEntityClass(tableName);
        Database database = tableClass.getAnnotation(Database.class);
        return Optional.ofNullable(database).map(Database::name).
                orElseThrow(() -> new ValidateException(ResultCode.PARAMS_ERROR.code(), StringUtils.join("Table '", tableName, "' not find database, please check")));
    }

    /**
     * 获取属性对应表的列名称
     *
     * @param tableName 表
     * @param fieldName 属性对象
     * @return 表对应的列名称
     */
    protected String getColumnName(String tableName, String fieldName) {
        try {
            Class<?> tableClass = this.getEntityClass(tableName);
            Field field = tableClass.getDeclaredField(fieldName);
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                return column.name();
            }
        } catch (NoSuchFieldException ignored) {
            throw new ValidateException(ResultCode.PARAMS_ERROR.code(), StringUtils.join("Column '", fieldName, "' not find in table, please check"));
        }
        throw new ValidateException(ResultCode.PARAMS_ERROR.code(), StringUtils.join("Column '", fieldName, "' not find in table, please check"));
    }

    /**
     * 整合sql，并赋值参数值
     *
     * @param sql       sql
     * @param paramsMap 参数值
     * @param linkStr   连接符
     * @return LinkedHashMap sql对应值
     */
    protected LinkedHashMap<String, Object> join(String tableName, StringBuilder sql, Map<String, Object> paramsMap, String linkStr) {
        LinkedHashMap<String, Object> paramsLink = new LinkedHashMap<>();
        paramsMap.forEach((k, v) -> {
            //特殊字符不需要进行拼接
            if (WHERE.equals(k) || COLUMN.equals(k) || ORDER.equals(k) || PAGE_NO.equals(k) || PAGE_SIZE.equals(k) || PAGE.equals(k) || TOTAL.equals(k) || ROWS.equals(k) || RESULT.equals(k)) {
                return;
            }
            //如果值为空也不需要拼接
            if (v == null) {
                return;
            }
            if (v instanceof String && ((String) v).length() <= 0) {
                return;
            }
            if (v instanceof List && ((List<?>) v).isEmpty()) {
                return;
            }
            String column = this.getColumnName(tableName, k);
            if (v instanceof List) {
                sql.append(column).append(" in (?)").append(linkStr).append(StringUtils.SPACE);
            } else {
                sql.append(column).append("=?").append(linkStr).append(StringUtils.SPACE);
            }

            if (v instanceof String && ((String) v).contains("->")) {
                //标识存在该字段即作为更新值也作为条件 只取更新后的值
                paramsLink.put(column, ((String) v).split("->")[1]);
            } else {
                paramsLink.put(column, v);
            }

        });
        //去除最后的连接字符
        sql.setLength(sql.length() - linkStr.length() - 1);
        return paramsLink;
    }

    /**
     * 组合select查询sql
     *
     * @param tableName 表名
     * @param sql       组合的sql
     * @param paramsMap 参数
     * @return LinkedHashMap sql对应值
     */
    protected LinkedHashMap<String, Object> select(String tableName, StringBuilder sql, Map<String, Object> paramsMap) {
        //判断是否存在查询列
        String column = "*";
        if (paramsMap.get(COLUMN) != null) {
            List<String> columns = new ArrayList<>();
            if (paramsMap.get(COLUMN) instanceof String) {
                columns = Arrays.asList(String.valueOf(paramsMap.get(COLUMN)).split(","));
            }
            if (paramsMap.get(COLUMN) instanceof List) {
                columns = (List<String>) paramsMap.get(COLUMN);
            }
            if (!columns.isEmpty()) {
                column = columns.stream().map(columnStr -> this.getColumnName(tableName, columnStr)).collect(Collectors.joining(","));
            }
        }
        //组合查询SQL
        sql.append("SELECT ").append(column).append(" FROM ").append(tableName).append(" WHERE ");
        //记录参数并保证与WHERE条件顺序一致
        LinkedHashMap<String, Object> paramsLink = this.join(tableName, sql, paramsMap, "AND");
        //排序
        sql.append(this.appendOrder(tableName, paramsMap.get(ORDER) == null ? null : String.valueOf(paramsMap.get(ORDER))));
        return paramsLink;
    }

    /**
     * 组合排序
     *
     * @param tableName 表名
     * @param order     排序字段
     * @return String 排序sql
     */
    protected String appendOrder(String tableName, String order) {
        if (StringUtils.isBlank(order)) {
            return StringUtils.EMPTY;
        }
        List<String> orders = Arrays.asList(order.split(","));
        StringBuilder orderSql = new StringBuilder(" ORDER BY ");
        orders.forEach(by -> {
            if (by.endsWith("-")) { //标识降序
                orderSql.append(this.getColumnName(tableName, by.replace("-", StringUtils.EMPTY))).append(StringUtils.SPACE).append("DESC").append(",").append(StringUtils.SPACE);
            } else { //+/没有都标识标识升序
                orderSql.append(this.getColumnName(tableName, by.replace("+", StringUtils.EMPTY))).append(StringUtils.SPACE).append("ASC").append(",").append(StringUtils.SPACE);
            }
        });
        //去除最后的连接字符
        orderSql.setLength(orderSql.length() - 2);
        return orderSql.toString();
    }

    protected Integer parseInt(String key, Object obj) {
        if (obj == null) {
            return Integer.MIN_VALUE;
        }
        int val;
        try {
            if (obj instanceof String || obj instanceof Integer) {
                val = Integer.parseInt(String.valueOf(obj));
            } else if (obj instanceof Double) {
                val = Double.valueOf(String.valueOf(obj)).intValue();
            } else if (obj instanceof Long) {
                val = Long.valueOf(String.valueOf(obj)).intValue();
            } else {
                throw new ValidateException(ResultCode.PARAMS_ERROR.code(), StringUtils.join("Illegal ", key, ", please check"));
            }
        } catch (NumberFormatException e) {
            throw new ValidateException(ResultCode.PARAMS_ERROR.code(), StringUtils.join("Illegal ", key, ", please check"));
        }

        return val;
    }

}
