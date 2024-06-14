package org.yimon.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yimon.admin.core.check.Validate;
import org.yimon.admin.core.exception.BusinessException;
import org.yimon.admin.core.exception.ValidateException;
import org.yimon.admin.dal.repository.CrudRepository;
import org.yimon.admin.service.ARepositoryService;
import org.yimon.admin.service.RepositoryService;
import org.yimon.admin.util.constant.ResultCode;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

/**
 * @author: ym.gao
 * @description: 更新
 * @date: 2024/5/6 17:04
 */
@Slf4j
@Service("post")
public class POST_Repository extends ARepositoryService implements RepositoryService {

    @Resource
    private CrudRepository crudRepository;

    @Override
    public Map<String, Object> execute(String tableName, Map<String, Object> paramsMap) {
        Validate.isNotBank(tableName, "tableName not be empty");
        Validate.isNonNull(paramsMap, "paramsMap not be empty");
        //组合更新SQL
        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        //记录参数并保证与需要更新的一致
        LinkedHashMap<String, Object> paramsLink = super.join(tableName, sql, paramsMap, ",");
        //去除最后一位逗号
        sql.append(" WHERE ");
        //获取where条件
        Object where = paramsMap.get(WHERE);
        if (where == null) {
            log.warn("POST not have where");
            throw new ValidateException(ResultCode.PARAMS_ERROR.code(), "post method must have @WHERE@ , please check");
        }
        List<String> whereList = Arrays.asList(String.valueOf(where).split(","));
        whereList.forEach(w -> {
            String column = super.getColumnName(tableName, w);
            sql.append(column).append("=? AND ");
            if (paramsMap.get(w) instanceof String && ((String) paramsMap.get(w)).contains("->")) {
                //标识存在该字段即作为更新值也作为条件 只取更新后的值
                paramsLink.put(column + WHERE, ((String) paramsMap.get(w)).split("->")[0]);
            } else {
                paramsLink.put(column + WHERE, paramsMap.get(w));
            }
        });
        sql.setLength(sql.length() - 4);

        Map<String, Object> result = new HashMap<>();
        result.put(ROWS, 0);//记录影响行数
        try {
            log.info("POST sql:{}, params:{}", sql, paramsLink);
            result.put(ROWS, crudRepository.operate(super.getDataBaseName(tableName), sql.toString(), paramsLink));
            return result;
        } catch (SQLException e) {
            log.error("POST sql exception:", e);
            if (e instanceof SQLSyntaxErrorException) {
                throw new ValidateException(ResultCode.PARAMS_ERROR.code(), e.getMessage());
            }
            throw new BusinessException(ResultCode.DB_FAIL.code(), e.getMessage());
        }
    }
}
