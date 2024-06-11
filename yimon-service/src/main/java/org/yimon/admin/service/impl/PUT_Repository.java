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
        Validate.isNotBank(tableName, "tableName not be empty");
        Validate.isNonNull(paramsMap, "paramsMap not be empty");
        //组合新增SQL
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append("(");
        //记录参数并保证与新增的一致
        LinkedHashMap<String, Object> paramsLink = super.join(tableName, sql, paramsMap, ",");
        //去除最后一位逗号
        sql = new StringBuilder(sql.toString().replaceAll("=\\?", "").replaceAll(" in \\(\\?\\)", ""));
        sql.append(") VALUES(");
        for (int i = 0; i < paramsLink.size(); i++) {
            sql.append("?, ");
        }
        sql.setLength(sql.length() - 2);
        sql.append(")");

        Map<String, Object> result = new HashMap<>();
        result.put(ROWS, 0);//记录影响行数
        try {
            log.info("PUT sql:{}, params:{}", sql, paramsLink);
            result.put(ROWS, crudRepository.operate(super.getDataBaseName(tableName), sql.toString(), paramsLink));
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
