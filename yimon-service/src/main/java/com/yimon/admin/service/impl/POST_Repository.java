package com.yimon.admin.service.impl;

import com.yimon.admin.core.exception.BusinessException;
import com.yimon.admin.core.exception.RejectedException;
import com.yimon.admin.dal.repository.CrudRepository;
import com.yimon.admin.service.ARepositoryService;
import com.yimon.admin.service.RepositoryService;
import com.yimon.admin.util.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
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
        //组合更新SQL
        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        //记录参数并保证与需要更新的一致
        LinkedHashMap<String, Object> paramsLink = join(sql, paramsMap, ",");
        //去除最后一位逗号
        sql.append(" WHERE ");
        //获取where条件
        Object where = paramsMap.get(WHERE);
        if (where == null) {
            log.warn("POST not have where");
            throw new RejectedException(ResultCode.REJECT.code(), "当前操作无条件，请求拒绝");
        }
        List<String> whereList = Arrays.asList(String.valueOf(where).split(","));
        whereList.forEach(w -> {
            sql.append(w).append("=? AND ");
            paramsLink.put(w + WHERE, paramsMap.get(w));
        });
        sql.setLength(sql.length() - 4);

        Map<String, Object> result = new HashMap<>();
        result.put(ROWS, 0);//记录影响行数
        try {
            log.info("POST sql:{}, params:{}", sql, paramsLink);
            result.put(ROWS, crudRepository.operate(sql.toString(), paramsLink));
            return result;
        } catch (SQLException e) {
            log.error("POST sql exception:", e);
            throw new BusinessException(ResultCode.DB_FAIL.code(), ResultCode.DB_FAIL.msg());
        }
    }
}
