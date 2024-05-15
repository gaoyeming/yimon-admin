package com.yimon.admin.service;

import com.yimon.admin.core.exception.ValidateException;
import com.yimon.admin.core.util.StringUtils;
import com.yimon.admin.util.constant.ResultCode;
import org.reflections.Reflections;

import javax.persistence.Table;
import java.util.*;

/**
 * @author: ym.gao
 * @description: crud抽象类，主要是提取公共方法
 * @date: 2024/5/8 15:44
 */
public abstract class ARepositoryService {

    protected static final String repositoryEntityPackage = "com.yimon.admin.dal.entity";

    protected static final String WHERE = "@WHERE@";

    protected static final String COLUMN = "@COLUMN@";

    protected static final String ORDER = "@ORDER@";

    protected static final String PAGE_NO = "@PAGE_NO@";

    protected static final String PAGE_SIZE = "@PAGE_SIZE@";

    protected static final String PAGE = "@PAGE@";

    protected static final String TOTAL = "@TOTAL@";

    protected static final String ROWS = "@ROWS@";

    protected static final String RESULT = "@RESULT@";

    protected Class<?> getEntityClass(String tableName) {
        //反射工具包，指明扫描路径
        Reflections reflections = new Reflections(repositoryEntityPackage);
        //获取带Handler注解的类
        Set<Class<?>> classList = reflections.getTypesAnnotatedWith(Table.class);
        return classList.stream().filter(clazz -> {
            Table table = clazz.getAnnotation(Table.class);
            return tableName.equals(table.name());
        }).findFirst().orElseThrow(() -> new ValidateException(ResultCode.PARAMS_ERROR.code(), StringUtils.join("Table '", tableName, "' doesn't exist, please check")));
    }

    protected LinkedHashMap<String, Object> join(StringBuilder sql, Map<String, Object> paramsMap, String linkStr) {
        LinkedHashMap<String, Object> paramsLink = new LinkedHashMap<>();
        paramsMap.forEach((k, v) -> {
            //特殊字符不需要进行拼接
            if (WHERE.equals(k) || COLUMN.equals(k) || ORDER.equals(k) || PAGE_NO.equals(k) || PAGE_SIZE.equals(k) || PAGE.equals(k) || TOTAL.equals(k) || ROWS.equals(k) || RESULT.equals(k)) {
                return;
            }
            if (v instanceof List) {
                sql.append(k).append(" in (?)").append(linkStr).append(StringUtils.SPACE);
            } else {
                sql.append(k).append("=?").append(linkStr).append(StringUtils.SPACE);
            }

            paramsLink.put(k, v);
        });
        //去除最后的连接字符
        sql.setLength(sql.length() - linkStr.length() - 1);
        return paramsLink;
    }

    protected LinkedHashMap<String, Object> select(StringBuilder sql, String tableName, Map<String, Object> paramsMap) {
        //判断是否存在查询列
        String column = "*";
        if (paramsMap.get(COLUMN) != null) {
            column = String.valueOf(paramsMap.get(COLUMN));
        }
        //组合查询SQL
        sql.append("SELECT ").append(column).append(" FROM ").append(tableName).append(" WHERE ");
        //记录参数并保证与WHERE条件顺序一致
        LinkedHashMap<String, Object> paramsLink = join(sql, paramsMap, "AND");
        //排序
        sql.append(appendOrder(paramsMap.get(ORDER) == null ? null : String.valueOf(paramsMap.get(ORDER))));
        return paramsLink;
    }

    protected String appendOrder(String order) {
        if (StringUtils.isBlank(order)) {
            return StringUtils.EMPTY;
        }
        List<String> orders = Arrays.asList(order.split(","));
        StringBuilder orderSql = new StringBuilder(" ORDER BY ");
        orders.forEach(by -> {
            if (by.endsWith("-")) { //标识降序
                orderSql.append(by.replace("-", StringUtils.EMPTY)).append(StringUtils.SPACE).append("DESC").append(",").append(StringUtils.SPACE);
            } else { //+/没有都标识标识升序
                orderSql.append(by.replace("+", StringUtils.EMPTY)).append(StringUtils.SPACE).append("ASC").append(",").append(StringUtils.SPACE);
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
