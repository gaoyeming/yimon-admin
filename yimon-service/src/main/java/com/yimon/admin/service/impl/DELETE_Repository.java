package com.yimon.admin.service.impl;

import com.yimon.admin.core.exception.BusinessException;
import com.yimon.admin.dal.repository.CrudRepository;
import com.yimon.admin.service.ARepositoryService;
import com.yimon.admin.service.RepositoryService;
import com.yimon.admin.util.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
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
        //组合删除SQL
        StringBuilder sql = new StringBuilder("DELETE FROM ").append(tableName).append(" WHERE ");
        //记录参数并保证与WHERE条件顺序一致
        LinkedHashMap<String, Object> paramsLink = join(sql, paramsMap, "AND");
        //执行sql并记录结果
        Map<String, Object> result = new HashMap<>();
        result.put(ROWS, 0);//记录影响行数
        try {
            log.info("DELETE sql:{}, params:{}", sql, paramsLink);
            result.put(ROWS, crudRepository.operate(sql.toString(), paramsLink));
            return result;
        } catch (SQLException e) {
            log.error("DELETE sql exception:", e);
            throw new BusinessException(ResultCode.DB_FAIL.code(), ResultCode.DB_FAIL.msg());
        }
    }
}
