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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: ym.gao
 * @description: 删除
 * @date: 2024/5/6 17:04
 */
@Slf4j
@Service("delete")
public class DELETE_Repository extends ARepositoryService implements RepositoryService {

    @Resource
    private CrudRepository crudRepository;

    @Override
    public Map<String, Object> execute(String tableName, Map<String, Object> paramsMap) {
        Validate.isNotBank(tableName, "tableName not be empty");
        Validate.isNonNull(paramsMap, "paramsMap not be empty");
        //组合删除SQL
        StringBuilder sql = new StringBuilder("DELETE FROM ").append(tableName).append(" WHERE ");
        //记录参数并保证与WHERE条件顺序一致
        LinkedHashMap<String, Object> paramsLink = super.join(tableName, sql, paramsMap, "AND");
        //执行sql并记录结果
        Map<String, Object> result = new HashMap<>();
        result.put(ROWS, 0);//记录影响行数
        try {
            log.info("DELETE sql:{}, params:{}", sql, paramsLink);
            result.put(ROWS, crudRepository.operate(super.getDataBaseName(tableName), sql.toString(), paramsLink));
            return result;
        } catch (SQLException e) {
            log.error("DELETE sql exception:", e);
            if (e instanceof SQLSyntaxErrorException) {
                throw new ValidateException(ResultCode.PARAMS_ERROR.code(), e.getMessage());
            }
            throw new BusinessException(ResultCode.DB_FAIL.code(), e.getMessage());
        }
    }
}
