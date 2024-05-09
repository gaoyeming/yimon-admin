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
 * @description: 查询单笔
 * @date: 2024/5/6 17:04
 */
@Slf4j
@Service("get")
public class GET_Repository extends ARepositoryService implements RepositoryService {

    @Resource
    private CrudRepository crudRepository;

    @Override
    public Map<String, Object> execute(String tableName, Map<String, Object> paramsMap) {
        //组合查询SQL
        StringBuilder sql = new StringBuilder();
        //记录参数并保证与WHERE条件顺序一致
        LinkedHashMap<String, Object> paramsLink = select(sql, tableName, paramsMap);
        //执行sql并记录结果
        Map<String, Object> result = new HashMap<>();
        result.put(TOTAL, 0);
        try {
            log.info("GET sql:{}, params:{}", sql, paramsLink);
            Object obj = crudRepository.selectFirst(sql.toString(), paramsLink, getEntityClass(tableName));
            if (obj != null) {
                result.put(TOTAL, 1);
            }
            result.put(RESULT, obj);
            return result;
        } catch (SQLException e) {
            log.error("GET sql exception:", e);
            throw new BusinessException(ResultCode.DB_FAIL.code(), ResultCode.DB_FAIL.msg());
        }
    }
}
