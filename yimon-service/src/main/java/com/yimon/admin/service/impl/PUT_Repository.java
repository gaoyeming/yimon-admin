package com.yimon.admin.service.impl;

import com.yimon.admin.core.exception.BusinessException;
import com.yimon.admin.core.exception.ValidateException;
import com.yimon.admin.dal.repository.CrudRepository;
import com.yimon.admin.service.ARepositoryService;
import com.yimon.admin.service.RepositoryService;
import com.yimon.admin.util.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: ym.gao
 * @description: 新增
 * @date: 2024/5/6 17:04
 */
@Slf4j
@Service("put")
public class PUT_Repository extends ARepositoryService implements RepositoryService {

    @Resource
    private CrudRepository crudRepository;

    @Override
    public Map<String, Object> execute(String tableName, Map<String, Object> paramsMap) {
        //组合新增SQL
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append("(");
        //记录values
        StringBuilder values = new StringBuilder();
        //记录参数并保证与新增的一致
        LinkedHashMap<String, Object> paramsLink = new LinkedHashMap<>();
        paramsMap.forEach((k, v) -> {
            //特殊字符不需要进行拼接
            if (WHERE.equals(k) || COLUMN.equals(k) || ORDER.equals(k) || PAGE_NO.equals(k) || PAGE_SIZE.equals(k) || PAGE.equals(k) || TOTAL.equals(k) || ROWS.equals(k) || RESULT.equals(k)) {
                return;
            }
            sql.append(k).append(", ");
            values.append("?, ");
            paramsLink.put(k, v);
        });
        //去除最后一位逗号
        sql.setLength(sql.length() - 2);
        sql.append(") ");
        sql.append("VALUES(");
        values.setLength(values.length() - 2);
        sql.append(values);
        sql.append(")");

        Map<String, Object> result = new HashMap<>();
        result.put(ROWS, 0);//记录影响行数
        try {
            log.info("PUT sql:{}, params:{}", sql, paramsLink);
            result.put(ROWS, crudRepository.operate(sql.toString(), paramsLink));
            return result;
        } catch (SQLException e) {
            log.error("PUT sql exception:", e);
            if (e instanceof SQLSyntaxErrorException) {
                throw new ValidateException(ResultCode.PARAMS_ERROR.code(), e.getMessage());
            }
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw new ValidateException(ResultCode.PARAMS_ERROR.code(), e.getMessage());
            }
            throw new BusinessException(ResultCode.DB_FAIL.code(), e.getMessage());
        }
    }
}
