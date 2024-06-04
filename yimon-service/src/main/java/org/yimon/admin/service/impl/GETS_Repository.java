package org.yimon.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
 * @description: 查询列表
 * @date: 2024/5/6 17:04
 */
@Slf4j
@Service("gets")
public class GETS_Repository extends ARepositoryService implements RepositoryService {

    @Resource
    private CrudRepository crudRepository;

    @Override
    public Map<String, Object> execute(String tableName, Map<String, Object> paramsMap) {
        //组合查询SQL
        StringBuilder listSql = new StringBuilder();
        //记录参数并保证与WHERE条件顺序一致
        LinkedHashMap<String, Object> paramsLink = super.select(tableName, listSql, paramsMap);
        //执行sql并记录结果
        Map<String, Object> result = this.queryTotal(tableName, paramsMap);
        try {
            log.info("GETS sql:{}, params:{}", listSql, paramsLink);
            result.put(RESULT, crudRepository.selectList(super.getDataBaseName(tableName), listSql.toString(), paramsLink,
                    super.parseInt(PAGE_NO, paramsMap.get(PAGE_NO)),
                    super.parseInt(PAGE_SIZE, paramsMap.get(PAGE_SIZE)),
                    super.getEntityClass(tableName)));
            return result;
        } catch (SQLException e) {
            log.error("GETS sql exception:", e);
            if (e instanceof SQLSyntaxErrorException) {
                throw new ValidateException(ResultCode.PARAMS_ERROR.code(), e.getMessage());
            }
            throw new BusinessException(ResultCode.DB_FAIL.code(), e.getMessage());
        }
    }

    private Map<String, Object> queryTotal(String tableName, Map<String, Object> paramsMap) {
        Map<String, Object> result = new HashMap<>();
        result.put(TOTAL, 0);
        //组合查询SQL
        StringBuilder countSql = new StringBuilder("SELECT COUNT(1) FROM ").append(tableName).append(" WHERE ");
        //记录参数并保证与WHERE条件顺序一致
        LinkedHashMap<String, Object> paramsLink = super.join(tableName, countSql, paramsMap, "AND");
        try {
            log.info("GETS total sql:{}, params:{}", countSql, paramsLink);
            Integer total = crudRepository.selectFirst(super.getDataBaseName(tableName), countSql.toString(), paramsLink, Integer.class);
            result.put(TOTAL, total);
        } catch (SQLException e) {
            log.error("GETS total sql exception:", e);
            if (e instanceof SQLSyntaxErrorException) {
                throw new ValidateException(ResultCode.PARAMS_ERROR.code(), e.getMessage());
            }
            throw new BusinessException(ResultCode.DB_FAIL.code(), e.getMessage());
        }
        //计算总页数
        if (super.parseInt(PAGE_NO, paramsMap.get(PAGE_NO)) > 0 || super.parseInt(PAGE_SIZE, paramsMap.get(PAGE_SIZE)) > 0) {
            Integer total = (Integer) result.get(TOTAL);
            Integer pageSize = super.parseInt(PAGE_SIZE, paramsMap.get(PAGE_SIZE));

            long totalPages = total / pageSize;
            if (total % pageSize != 0) {
                totalPages++;
            }
            result.put(PAGE, totalPages);
        }
        return result;
    }
}
